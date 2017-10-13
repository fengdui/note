package com.fengdui.note.nettyaction.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * AIOServer
 *
 * @author FD
 * @date 2016/5/13
 */
public class AIOServer implements Runnable {

    private int port;
    CountDownLatch countDownLatch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AIOServer(int port) {
        this.port = port;

        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("com.fengdui.note.nettyaction.aio 服务端启动");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        countDownLatch = new CountDownLatch(1);
        doAccept();
    }
    public void doAccept() {
        asynchronousServerSocketChannel.accept(this, new AIOHandler());
    }
}
