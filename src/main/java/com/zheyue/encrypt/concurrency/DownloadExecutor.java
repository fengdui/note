package com.zheyue.encrypt.concurrency;

import com.google.common.util.concurrent.*;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author FD
 * @date 2017/1/3
 */
public class DownloadExecutor {

    private volatile static ListeningExecutorService threadPoolExecutor;

    public static ListeningExecutorService getThreadPoolExecutor(){
        if (threadPoolExecutor == null) {
            synchronized (DownloadExecutor.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = MoreExecutors.listeningDecorator(getJDkThreadPoolExecutor());
                }
            }
        }
        return threadPoolExecutor;
    }

    private static ExecutorService getJDkThreadPoolExecutor() {
        return Executors.newFixedThreadPool(10);
    }
    public static void submit(Callable<Boolean> task) {
        ListenableFuture<Boolean> listenableFuture = getThreadPoolExecutor().submit(task);
        Futures.addCallback(listenableFuture, new FutureCallback<Boolean>() {
            public void onSuccess(Boolean result) {
//                ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
//                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                        System.out.println("RPC Server Send message-id respone:" + request.getMessageId());
//                    }
//                });
                System.out.println("下载成功");
            }

            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, threadPoolExecutor);
    }
}
