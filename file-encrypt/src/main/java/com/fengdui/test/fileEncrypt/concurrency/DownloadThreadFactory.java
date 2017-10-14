package com.fengdui.test.fileEncrypt.concurrency;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author FD
 * @date 2017/1/11
 * 线程工厂，实现重命名，可在日志中查看
 */
public class DownloadThreadFactory extends NamedThreadFactory{

    public DownloadThreadFactory() {
        super("download-threadPool-");
    }
}
