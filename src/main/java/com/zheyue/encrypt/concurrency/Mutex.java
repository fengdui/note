package com.zheyue.encrypt.concurrency;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author FD
 * @date 2017/2/9
 */
public class Mutex {
    private static class Sync extends AbstractQueuedSynchronizer {

    }

    private final Sync sync;

    public Mutex(Sync sync) {
        this.sync = new Sync();
    }
}
