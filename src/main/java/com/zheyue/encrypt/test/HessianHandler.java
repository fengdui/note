package com.zheyue.encrypt.test;

import com.zheyue.encrypt.model.DownloadRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author FD
 * @date 2016/12/30
 */
public class HessianHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接成功");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("注册成功");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DownloadRequest request = (DownloadRequest) msg;
        System.out.println(request.getRequestId());
        System.out.println(request.getFileNum());
        System.out.println(request.getFileId());
    }
}
