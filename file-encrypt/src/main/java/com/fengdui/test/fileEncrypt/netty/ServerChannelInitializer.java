package com.fengdui.test.fileEncrypt.netty;

import com.fengdui.test.fileEncrypt.concurrency.DownloadExecutor;
import com.fengdui.test.fileEncrypt.netty.handler.DownloadHandler;
import com.fengdui.test.fileEncrypt.serialize.SerializeProtocol;
import com.fengdui.test.fileEncrypt.serialize.hessian.HessianDecoder;
import com.fengdui.test.fileEncrypt.serialize.hessian.HessianEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author FD
 * @date 2016/12/30
 * netty初始化，配置序列化方式
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {


    private SerializeProtocol serializeProtocol;

    private DownloadExecutor downloadExecutor;

    public ServerChannelInitializer(SerializeProtocol serializeProtocol, DownloadExecutor downloadExecutor) {
        this.serializeProtocol = serializeProtocol;
        this.downloadExecutor = downloadExecutor;
    }

    /**
     * 目前只实现hessian
     * @param ch
     * @throws Exception
     */
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
