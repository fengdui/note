package com.zheyue.encrypt.concurrency;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author FD
 * @date 2017/1/11
 */
public class DownloadThreadFactory extends NamedThreadFactory{

    public DownloadThreadFactory() {
        super("download-threadPool-");
    }
}
