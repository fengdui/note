package com.zheyue.encrypt.netty.handler;

import com.zheyue.encrypt.concurrency.DownloadExecutor;
import com.zheyue.encrypt.concurrency.DownloadTask;
import com.zheyue.encrypt.model.DownloadRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Arrays;

/**
 * @author FD
 * @date 2016/12/30
 */
public class DownloadHandler extends ChannelInboundHandlerAdapter {

    long start;
    long end;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("连接成功");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("注册成功");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        start = System.currentTimeMillis();
        System.out.println(start);
        DownloadRequest request = (DownloadRequest) msg;
        System.out.println(request.getId());
        DownloadTask task = new DownloadTask(request, ctx);
        DownloadExecutor.submit(task);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("read 完成");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("断开连接");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("取消注册");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("客户端断开");
        ctx.close();
    }
}
