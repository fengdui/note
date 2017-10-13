package serializable.client;

import entity.BaseMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author FD
 * @data 2016/11/4
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<BaseMsg>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMsg msg) throws Exception {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }
}
