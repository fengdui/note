package com.fengdui.test.zookeeper.origin;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author FD
 * @date 2017/10/17
 */
public class Async_Usage {
    public static void main(String[] args) throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 1000, null);
    }
}
