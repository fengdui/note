package com.zheyue.encrypt.concurrency;

import com.google.common.util.concurrent.*;
import com.zheyue.encrypt.model.DownloadRequest;
import com.zheyue.encrypt.model.DownloadResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author FD
 * @date 2017/1/3
 */
public class DownloadExecutor {

    Logger LOGGER = LoggerFactory.getLogger(DownloadExecutor.class);

    private volatile ListeningExecutorService threadPoolExecutor;

    //下载文件线程池个数
    private int threads = 10;


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

    private ExecutorService getJDkThreadPoolExecutor() {
        return Executors.newFixedThreadPool(threads);
    }
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
