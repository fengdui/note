package com.zheyue.encrypt.test;

import com.zheyue.encrypt.model.DownloadRequest;
import com.zheyue.encrypt.model.DownloadResponse;
import com.zheyue.encrypt.service.AesService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author FD
 * @date 2017/1/3
 */
public class FileReciveTest extends ChannelInboundHandlerAdapter {


    Logger LOGGER = LoggerFactory.getLogger(FileReciveTest.class);

    private ConcurrentHashMap<String, OutputStream> requestMap;

    private AesService aesService = new AesService();

    public FileReciveTest(ConcurrentHashMap<String, OutputStream> requestMap) {
        this.requestMap = requestMap;
    }


    public void sendRequest(ChannelHandlerContext ctx, DownloadRequest request) {
        ctx.writeAndFlush(request);


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
        System.out.println(new Date(System.currentTimeMillis()));
        int userId = new Random().nextInt(100);
        for (int i = 0; i < 1; i++) {
            String requestId = UUID.randomUUID().toString();
            FileOutputStream fileOutputStream = new FileOutputStream("D:\\jmeter\\result\\"+requestId+"---"+userId+".pdf");
            DownloadRequest downloadRequest = new DownloadRequest();
            downloadRequest.setUserId(userId);
            downloadRequest.setFileId(2);
            downloadRequest.setRequestId(requestId);

            sendRequest(ctx, downloadRequest);

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
            System.out.println(new Date(System.currentTimeMillis()));
        }
        else {
//            byte[] data = response.getData();
//            System.out.println(response.getData().length);
//            System.out.println(response.getLength());
            byte[] data = aesService.decrypt(response.getData(), response.getUserId());
            outputStream.write(data);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }
}
