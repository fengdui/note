package com.zheyue.encrypt.test;

import com.zheyue.encrypt.model.DownloadRequest;
import com.zheyue.encrypt.model.DownloadResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author FD
 * @date 2017/1/3
 */
public class FileReciveTest extends ChannelInboundHandlerAdapter {

    private ConcurrentHashMap<String, OutputStream> requestMap;

    public FileReciveTest(ConcurrentHashMap<String, OutputStream> requestMap) {
        this.requestMap = requestMap;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //一个用户测试3次
        int userId = new Random().nextInt()%1000;
        for (int i = 0; i < 1; i++) {
            String requestId = UUID.randomUUID().toString();
            FileOutputStream fileOutputStream = new FileOutputStream("D:\\jmeter\\result\\"+requestId+"---"+userId);
            DownloadRequest downloadRequest = new DownloadRequest();
            downloadRequest.setUserId(userId);
            downloadRequest.setFileId(2);
            ctx.writeAndFlush(downloadRequest);
            requestMap.putIfAbsent(requestId, fileOutputStream);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DownloadResponse response = (DownloadResponse)msg;
        String requestId = response.getRequestId();
        FileOutputStream outputStream = (FileOutputStream) requestMap.get(requestId);
        if (response.isEOF()) {
            outputStream.close();
            requestMap.remove(requestId);
        }
        else {
            outputStream.write(response.getData());
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }
}
