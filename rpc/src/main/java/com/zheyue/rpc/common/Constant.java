package com.zheyue.rpc.common;

public interface Constant {

	int ZK_SESSION_TIMEOUT = 5000;

    String ZK_REGISTRY_PATH = "/zookeeper/quota";
    String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/data";
}