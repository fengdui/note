package com.zheyue.encrypt.netty.handler;

import com.zheyue.encrypt.concurrency.DownloadExecutor;
import com.zheyue.encrypt.concurrency.DownloadTask;
import com.zheyue.encrypt.model.DownloadRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * @author FD
 * @date 2016/12/30
 */
public class DownloadHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private DownloadExecutor downloadExecutor;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("fd");

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("fd");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("fd");
        DownloadRequest request = (DownloadRequest) msg;
        DownloadTask task = new DownloadTask(request, ctx);
        downloadExecutor.submit(task, ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("fd");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("fd");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("fd");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("fd");
        ctx.close();
    }
}
