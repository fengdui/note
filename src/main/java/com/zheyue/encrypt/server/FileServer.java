package com.zheyue.encrypt.server;

import com.zheyue.encrypt.annotation.RpcService;
import com.zheyue.encrypt.netty.ServerChannelInitializer;
import com.zheyue.encrypt.serialize.SerializeProtocol;
import com.zheyue.encrypt.zookeeper.ServiceRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * @author FD
 * @date 2016/12/29
 */
public class FileServer implements ApplicationContextAware{

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServer.class);

//    @Value("${server.address}")
    private String serverAddress;
//    @Autowired
//    private ServiceRegistry serviceRegistry;

    private int parallel;
    private SerializeProtocol serializeProtocol;

    private EventLoopGroup boss = new NioEventLoopGroup();

    private EventLoopGroup worker = new NioEventLoopGroup(parallel);

    private Map<String, Object> handlerMap = new HashMap<>();

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (serviceMap != null && serviceMap.size() > 0) {
            for (Object serviceBean : serviceMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
                handlerMap.put(interfaceName, serviceBean);
            }
        }
    }

    public void start() throws Exception {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ServerChannelInitializer(serializeProtocol, handlerMap))
                    .option(ChannelOption.SO_BACKLOG, 1024);
            String[] ipAddr = serverAddress.split(":");
            String host = ipAddr[0];
            int port = Integer.parseInt(ipAddr[1]);
            System.out.println("fd");
            ChannelFuture future = b.bind(host, port).sync();
            LOGGER.debug("server started on port {}", port);
//            if (serviceRegistry != null) {
//                System.out.println("zhuce");
//                serviceRegistry.register(serverAddress);
//            }
            future.channel().closeFuture().sync();
        }
        finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
