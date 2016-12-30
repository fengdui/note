package com.zheyue.encrypt.netty;

import com.zheyue.encrypt.serialize.SerializeProtocol;
import com.zheyue.encrypt.serialize.hessian.HessianDecoder;
import com.zheyue.encrypt.serialize.hessian.HessianEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import java.util.Map;

/**
 * @author FD
 * @date 2016/12/30
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private SerializeProtocol serializeProtocol;
    private Map<String, Object> handlerMap = null;

    public ServerChannelInitializer(SerializeProtocol serializeProtocol, Map<String, Object> handlerMap) {
        this.serializeProtocol = serializeProtocol;
        this.handlerMap = handlerMap;
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
                ch.pipeline().addLast(new HessianDecoder()).addLast(new HessianEncoder());
                break;
            }
            case PROTOSTUFFSERIALIZE: {
                break;
            }
        }
    }
}
