package com.zheyue.encrypt.concurrency;

/**
 * @author FD
 * @date 2017/1/11
 */
public class NettyThreadFactory extends NamedThreadFactory{

    public NettyThreadFactory() {
        super("nettyGroup-threadPool-");
    }
}
