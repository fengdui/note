package com.zheyue.encrypt.serialize.hessian;

import com.caucho.hessian.io.HessianFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;

/**
 * @author FD
 * @date 2016/12/30
 * hessian序列化编码码
 */
public class HessianEncoder extends MessageToByteEncoder<Object>{


    private HessianSerializePool pool = HessianSerializePool.getHessianPoolInstance();

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        HessianSerialize hessianSerialize = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            hessianSerialize = pool.borrow();
            hessianSerialize.serialize(byteArrayOutputStream, msg);
            byte[] body = byteArrayOutputStream.toByteArray();
            int dataLength = body.length;
            out.writeInt(dataLength);
            out.writeBytes(body);
        } finally {
            pool.restore(hessianSerialize);
        }
    }
}
