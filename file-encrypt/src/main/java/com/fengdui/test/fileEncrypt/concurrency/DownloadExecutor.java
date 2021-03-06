package com.fengdui.test.fileEncrypt.concurrency;

import com.google.common.util.concurrent.*;
import com.fengdui.test.fileEncrypt.model.DownloadRequest;
import com.fengdui.test.fileEncrypt.model.DownloadResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.*;

/**
 * @author FD
 * @date 2017/1/3
 * 线程调度池，维护一个DownloadTaskThreadPool，负责向线程池提交任务
 */
public class DownloadExecutor {

    Logger LOGGER = LoggerFactory.getLogger(DownloadExecutor.class);

    private volatile ListeningExecutorService threadPoolExecutor;

    //TODO 应该可配置 需要修改
    //下载文件线程池个数 可以配置
    private int threads = 10;


    /**
     * 单例 使用getJDkThreadPoolExecutor获取之后使用guava封装
     * @return
     */
    public ListeningExecutorService getThreadPoolExecutor(){
        if (threadPoolExecutor == null) {
            synchronized (DownloadExecutor.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = MoreExecutors.listeningDecorator(getJDkThreadPoolExecutor());
                }
            }
        }
        return threadPoolExecutor;
    }

    /**
     * 获取DownloadTaskThreadPool，继承ThreadPoolExecutor
     * @return
     */
    private ExecutorService getJDkThreadPoolExecutor() {
//        return Executors.newFixedThreadPool(threads);
        return new DownloadTaskThreadPool(threads, threads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new DownloadThreadFactory());
    }

    /**
     * 提交一个 Callable类型的task到线程池，实现回调
     * @param task
     * @param ctx
     */
    public void submit(Callable<Boolean> task, ChannelHandlerContext ctx) {
        ListenableFuture<Boolean> listenableFuture = getThreadPoolExecutor().submit(task);
        Futures.addCallback(listenableFuture, new FutureCallback<Boolean>() {
            public void onSuccess(Boolean result) {
                DownloadRequest request= ((DownloadTask) task).getRequest();
                if (result == Boolean.TRUE) {
                    DownloadResponse response = new DownloadResponse();
                    response.setIsEOF(true);
                    response.setRequestId(request.getRequestId());
                    ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            LOGGER.info(request.getUserId() + " " + request.getRequestId() + " " + "下载完毕");
                        }
                    });
                }
                else {

                }
            }

            public void onFailure(Throwable t) {
                LOGGER.error(t.getMessage());
            }
        }, threadPoolExecutor);
    }
}
