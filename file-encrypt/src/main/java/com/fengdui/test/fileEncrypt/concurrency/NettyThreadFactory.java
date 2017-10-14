package com.zheyue.encrypt.concurrency;

/**
 * @author FD
 * @date 2017/1/11
 * 线程工厂，实现重命名，可在日志中查看
 */
public class NettyThreadFactory extends NamedThreadFactory{

    public NettyThreadFactory() {
        super("nettyGroup-threadPool-");
    }
}
