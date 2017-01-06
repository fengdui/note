package com.zheyue.encrypt.serialize;

import io.netty.channel.ChannelPipeline;

import java.util.Map;

/**
 * @author FD
 * @date 2016/12/30
 */
public interface SerializeInitializer {
    void init(Map<String, Object> handlerMap, ChannelPipeline pipeline);
}
