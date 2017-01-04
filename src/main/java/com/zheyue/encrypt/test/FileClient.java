package com.zheyue.encrypt.test;

import com.zheyue.encrypt.netty.ServerChannelInitializer;
import com.zheyue.encrypt.serialize.SerializeProtocol;
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

import java.util.HashMap;
import java.util.Map;

/**
 * @author FD
 * @date 2017/1/3
 */
public class FileClient {

    private SerializeProtocol serializeProtocol = SerializeProtocol.HESSIANSERIALIZE;

    private EventLoopGroup boss = new NioEventLoopGroup();

    private EventLoopGroup worker = new NioEventLoopGroup(10);

    private Map<String, Object> handlerMap = new HashMap<>();

    public void start() throws Exception {
        try {
            Bootstrap b = new Bootstrap();
            b.group(worker).channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer(serializeProtocol))
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = b.connect("localhost", 9999).sync();
            future.channel().closeFuture().sync();
        }
        finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new FileClient().start();
    }
}
