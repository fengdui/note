package com.zheyue.encrypt.test;

import com.zheyue.encrypt.model.DownloadRequest;
import com.zheyue.encrypt.model.DownloadResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author FD
 * @date 2017/1/3
 */
public class FileReciveTest extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("注册");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("取消注册");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端发送请求");
        DownloadRequest downloadRequest = new DownloadRequest();
        downloadRequest.setFileNum(1);
        downloadRequest.setFileId(new int[]{2});
        ctx.writeAndFlush(downloadRequest);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("断开");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DownloadResponse response = (DownloadResponse)msg;
        System.out.println("f");
    }
}
