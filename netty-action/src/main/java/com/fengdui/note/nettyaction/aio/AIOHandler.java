package com.fengdui.note.nettyaction.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * AIOHandler
 *
 * @author FD
 * @date 2016/5/13
 */
public class AIOHandler implements CompletionHandler<AsynchronousSocketChannel, AIOServer>{

    public void completed(AsynchronousSocketChannel result, AIOServer attachment) {
        attachment.asynchronousServerSocketChannel.accept(attachment, this);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        result.read(byteBuffer, byteBuffer, new ReadCompletionHandle(result));
    }

    public void failed(Throwable exc, AIOServer attachment) {
        exc.printStackTrace();
        attachment.countDownLatch.countDown();
    }
}