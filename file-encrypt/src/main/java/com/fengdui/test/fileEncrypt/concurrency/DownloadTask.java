package com.zheyue.encrypt.concurrency;

import com.zheyue.encrypt.common.AppContext;
import com.zheyue.encrypt.model.DownloadRequest;
import com.zheyue.encrypt.service.DownloadService;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.Callable;

/**
 * @author FD
 * @date 2017/1/3
 * 下载任务
 */
public class DownloadTask implements Callable<Boolean>{

    private DownloadRequest request;

    private ChannelHandlerContext ctx;

    public DownloadTask(DownloadRequest request, ChannelHandlerContext ctx) {
        this.request = request;
        this.ctx = ctx;
    }

    public DownloadRequest getRequest() {
        return request;
    }

    public void setRequest(DownloadRequest request) {
        this.request = request;
    }


    /**
     * 提交至线程池之后，会调用call方法（）
     * 获取spring容器中的DownloadService开始下载
     * @return
     * @throws Exception
     */
    @Override
    public Boolean call() throws Exception {
        try {
            DownloadService serviceBean = (DownloadService)AppContext.getBean("downloadService");
            return serviceBean.downloadFromOss(request, ctx);
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
