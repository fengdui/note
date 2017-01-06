package com.zheyue.encrypt.test;

import com.zheyue.encrypt.serialize.SerializeProtocol;
import com.zheyue.encrypt.serialize.hessian.HessianDecoder;
import com.zheyue.encrypt.serialize.hessian.HessianEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author FD
 * @date 2016/12/30
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private SerializeProtocol serializeProtocol;

    public ClientChannelInitializer(SerializeProtocol serializeProtocol) {
        this.serializeProtocol = serializeProtocol;
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
                ch.pipeline().addLast(new HessianDecoder()).addLast(new HessianEncoder()).addLast(new FileReciveTest());
                break;
            }
            case PROTOSTUFFSERIALIZE: {
                break;
            }
        }
    }
}
