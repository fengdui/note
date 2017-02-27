package com.zheyue.encrypt.netty.handler;

import com.zheyue.encrypt.concurrency.DownloadExecutor;
import com.zheyue.encrypt.concurrency.DownloadTask;
import com.zheyue.encrypt.model.DownloadRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * @author FD
 * @date 2016/12/30
 * netty 处理，经过解码之后会到此
 */
public class DownloadHandler extends ChannelInboundHandlerAdapter {

    Logger LOGGER = LoggerFactory.getLogger(DownloadHandler.class);

    private DownloadExecutor downloadExecutor;

    public DownloadHandler(DownloadExecutor downloadExecutor) {
        this.downloadExecutor = downloadExecutor;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    }

    /**
     * hessian解码之后，直接强转成DownloadRequest
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DownloadRequest request = (DownloadRequest) msg;
        DownloadTask task = new DownloadTask(request, ctx);
        downloadExecutor.submit(task, ctx);
        LOGGER.info(Thread.currentThread().getName());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error(cause.getMessage());
        ctx.close();
    }
}
