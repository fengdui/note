package com.fengdui.test.fileEncrypt.serialize.hessian;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author FD
 * @date 2016/12/30
 * hessian序列化解码
 */
public class HessianDecoder extends ByteToMessageDecoder {

    //使用一个int 4个字节表示序列化的长度
    public static final int MESSAGE_LENGTH = 4;

    //
    private HessianSerializePool pool = HessianSerializePool.getHessianPoolInstance();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        //首先读取一个int messageLength，表示一个DownloadRequest请求的字节长度，如果不能读到一个字节，直接返回
        if (in.readableBytes() < HessianDecoder.MESSAGE_LENGTH) {
            return;
        }

        in.markReaderIndex();
        //messageLength表示DownloadRequest请求的字节长度
        int messageLength = in.readInt();

        if (messageLength < 0) {
            ctx.close();
        }

        //只读取到部分，返回
        if (in.readableBytes() < messageLength) {
            in.resetReaderIndex();
            return;
        } else {
            byte[] messageBody = new byte[messageLength];
            in.readBytes(messageBody);
            Object obj = decode(messageBody);
            out.add(obj);
        }
    }

    /**
     * 将字节数组反序列化成DownloadRequest
     * @param body
     * @return
     */
    private Object decode(byte[] body) {
        HessianSerialize hessianSerialize = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            hessianSerialize = pool.borrow();
            Object object = hessianSerialize.deserialize(byteArrayInputStream);
            return object;
        } finally {
            pool.restore(hessianSerialize);
        }
    }
}
