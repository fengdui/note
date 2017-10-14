package com.fengdui.test.fileEncrypt.test;

import com.fengdui.test.fileEncrypt.serialize.SerializeProtocol;
import com.fengdui.test.fileEncrypt.serialize.hessian.HessianDecoder;
import com.fengdui.test.fileEncrypt.serialize.hessian.HessianEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author FD
 * @date 2016/12/30
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private SerializeProtocol serializeProtocol;

    private ConcurrentHashMap<String, OutputStream> requestMap;


    public ClientChannelInitializer(SerializeProtocol serializeProtocol) {
        this.serializeProtocol = serializeProtocol;
        requestMap = new ConcurrentHashMap<>();
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        if (serializeProtocol == null) {
            throw new Exception("serializeProtocol 不允许为空");
        }
        switch (serializeProtocol) {
            case JDKSERIALIZE: {
                break;
            }
            case KRYOSERIALIZE: {
                break;
            }
            case HESSIANSERIALIZE: {
                ch.pipeline().addLast(new HessianDecoder()).addLast(new HessianEncoder()).addLast(new FileReciveTest(requestMap));
                break;
            }
            case PROTOSTUFFSERIALIZE: {
                break;
            }
        }
    }
}
