package com.zheyue.encrypt.server;

import com.zheyue.encrypt.netty.ServerChannelInitializer;
import com.zheyue.encrypt.serialize.SerializeProtocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * @author FD
 * @date 2016/12/29
 */

public class FileServer{

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServer.class);

    @Value("${file.server.address}")
    private String serverAddress;

    @Value("${file.server.nThreads}")
    private int parallel;

    private SerializeProtocol serializeProtocol = SerializeProtocol.HESSIANSERIALIZE;

    private EventLoopGroup boss = new NioEventLoopGroup();

    private EventLoopGroup worker = new NioEventLoopGroup(10);


    public void start() throws Exception {
        LOGGER.info("server start");
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ServerChannelInitializer(serializeProtocol))
                    .option(ChannelOption.SO_BACKLOG, 1024);
            String[] ipAddr = serverAddress.split(":");
            String host = ipAddr[0];
            int port = Integer.parseInt(ipAddr[1]);
            ChannelFuture future = b.bind(port).sync();
            future.channel().closeFuture().sync();
        }
        finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
