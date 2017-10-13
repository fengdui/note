package com.fengdui.note.nettyaction.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;

import java.util.List;
import java.util.Map;

/**
 * WebSocketHandler
 *
 * @author FD
 * @date 2016/5/13
 */
public class WebSocketHandler extends SimpleChannelInboundHandler{

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println(o instanceof WebSocketFrame);
        System.out.println(o instanceof FullHttpRequest);
        if (o instanceof  FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest)o;
            HttpHeaders headers = request.headers();
            List<Map.Entry<String, String>> entries = headers.entries();
            for (Map.Entry<String, String> entry : entries) {
                System.out.println(entry.getKey()+"  "+entry.getValue());
            }
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:9999", null, false);
            WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(request);
            handshaker.handshake(channelHandlerContext.channel(), request);
        }
        else if (o instanceof WebSocketFrame){
            WebSocketFrame frame = (WebSocketFrame) o;
        }
        else {
//            channelHandlerContext.channel()
        }
    }
}
