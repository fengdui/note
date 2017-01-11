package com.zheyue.encrypt.netty;

import com.zheyue.encrypt.concurrency.DownloadExecutor;
import com.zheyue.encrypt.netty.handler.DownloadHandler;
import com.zheyue.encrypt.serialize.SerializeProtocol;
import com.zheyue.encrypt.serialize.hessian.HessianDecoder;
import com.zheyue.encrypt.serialize.hessian.HessianEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author FD
 * @date 2016/12/30
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {


    private SerializeProtocol serializeProtocol;

    private DownloadExecutor downloadExecutor;

    public ServerChannelInitializer(SerializeProtocol serializeProtocol, DownloadExecutor downloadExecutor) {
        this.serializeProtocol = serializeProtocol;
        this.downloadExecutor = downloadExecutor;
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
                ch.pipeline().addLast(new HessianDecoder()).addLast(new HessianEncoder()).addLast(new DownloadHandler(downloadExecutor));
                break;
            }
            case PROTOSTUFFSERIALIZE: {
                break;
            }
        }
    }
}
