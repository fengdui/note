package com.fengdui.test.fileEncrypt.test;

import com.fengdui.test.fileEncrypt.netty.ServerChannelInitializer;
import com.fengdui.test.fileEncrypt.serialize.SerializeProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author FD
 * @date 2017/1/3
 */
public class FileClient {

    private SerializeProtocol serializeProtocol = SerializeProtocol.HESSIANSERIALIZE;

    private EventLoopGroup boss = new NioEventLoopGroup();

    private EventLoopGroup worker = new NioEventLoopGroup(10);

    public void start() throws Exception {
        try {
            Bootstrap b = new Bootstrap();
            b.group(worker).channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer(serializeProtocol))
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = b.connect("192.168.0.53", 8000).sync();
            future.channel().closeFuture().sync();
        }
        finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static class myThread implements Runnable {

        @Override
        public void run() {
            try {
                new FileClient().start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws Exception {
        int i = 5_30;
        int x = 0b110;
        System.out.println(x);
//        System.out.println(1483500827581-);
//        for (int i = 0; i < 2; i++) {
//            new Thread(new myThread()).start();
//        }
    }
}
