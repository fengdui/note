package com.zheyue.encrypt.test;

import com.zheyue.encrypt.netty.ServerChannelInitializer;
import com.zheyue.encrypt.serialize.SerializeProtocol;
import io.netty.bootstrap.Bootstrap;
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
 * @date 2017/1/3
 */
public class FileClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileClient.class);

    @Value("${file.server.address}")
    private String serverAddress;
//    @Autowired
//    private ServiceRegistry serviceRegistry;

    private int parallel;

    //    @Value("${serialize.protocol}")
    private SerializeProtocol serializeProtocol = SerializeProtocol.HESSIANSERIALIZE;

    private EventLoopGroup boss = new NioEventLoopGroup();

    private EventLoopGroup worker = new NioEventLoopGroup(parallel);

    private Map<String, Object> handlerMap = new HashMap<>();

//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(RpcService.class);
//        if (serviceMap != null && serviceMap.size() > 0) {
//            for (Object serviceBean : serviceMap.values()) {
//                String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
//                handlerMap.put(interfaceName, serviceBean);
//            }
//        }
//    }

    public void start() throws Exception {
        try {
            Bootstrap b = new Bootstrap();
            b.group(worker).channel(NioServerSocketChannel.class)
                    .handler(new ServerChannelInitializer(serializeProtocol, handlerMap))
                    .option(ChannelOption.SO_BACKLOG, 1024);
            String[] ipAddr = serverAddress.split(":");
            String host = ipAddr[0];
            int port = Integer.parseInt(ipAddr[1]);
            ChannelFuture future = b.connect(host, port);
            LOGGER.debug("client started on port {}", port);

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
