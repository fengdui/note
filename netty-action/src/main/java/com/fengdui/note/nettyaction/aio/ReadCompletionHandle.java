package com.fengdui.note.nettyaction.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * ReadCompletionHandle
 *
 * @author FD
 * @date 2016/5/26
 */
public class ReadCompletionHandle implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ReadCompletionHandle(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] bytes = new byte[attachment.remaining()];
        attachment.get(bytes);
        try {
            System.out.println(new String(bytes, "UTF-8"));
            doWriter();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public void doWriter() {
        try {
            byte[] bytes = "服务器发回消息".getBytes("UTF-8");
            final ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            channel.write(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                public void completed(Integer result, ByteBuffer attachment) {
                    if (byteBuffer.hasRemaining()) {
                        channel.write(byteBuffer, byteBuffer, this);
                    }
                }

                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
                    try {
                        channel.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            try {
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void failed(Throwable exc, ByteBuffer attachment) {
        exc.printStackTrace();
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
