package com.zheyue.encrypt.serialize.hessian;

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
 */
public class HessianDecoder extends ByteToMessageDecoder {

    //使用一个int 4个字节表示序列化的长度
    public static final int MESSAGE_LENGTH = 4;

    private HessianSerializePool pool = HessianSerializePool.getHessianPoolInstance();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() < HessianDecoder.MESSAGE_LENGTH) {
            return;
        }

        in.markReaderIndex();
        int messageLength = in.readInt();

        if (messageLength < 0) {
            ctx.close();
        }

        if (in.readableBytes() < messageLength) {
            in.resetReaderIndex();
            return;
        }
        else {
            byte[] messageBody = new byte[messageLength];
            in.readBytes(messageBody);
            Object obj = decode(messageBody);
            out.add(obj);
        }
    }

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
