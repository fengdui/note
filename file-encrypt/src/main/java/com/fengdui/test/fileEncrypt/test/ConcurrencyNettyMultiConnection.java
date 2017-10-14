package com.fengdui.test.fileEncrypt.test;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @author FD
 * @date 2017/1/10
 */
public class ConcurrencyNettyMultiConnection {

//    public static void main(String args[]) {
//        final ClientBootstrap bootstrap = new ClientBootstrap(
//                new NioClientSocketChannelFactory(
//                        Executors.newCachedThreadPool(),
//                        Executors.newCachedThreadPool()));
//        // 设置一个处理服务端消息和各种消息事件的类(Handler)
//        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
//            @Override
//            public ChannelPipeline getPipeline() throws Exception {
//                return Channels.pipeline(
//                );
//            }
//        });
//        for (int i = 0; i < 3; i++) {
//            bootstrap.connect(new InetSocketAddress("127.0.0.1", 8000));
//        }
//    }
}