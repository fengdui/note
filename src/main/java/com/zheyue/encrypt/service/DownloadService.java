package com.zheyue.encrypt.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.zheyue.encrypt.model.DownloadRequest;
import com.zheyue.encrypt.model.DownloadResponse;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.Key;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author FD
 * @date 2017/1/3
 */

@Service
public class DownloadService {

    Logger LOGGER = LoggerFactory.getLogger(DownloadService.class);

    //默认一块64kb
    private static final int BLOCK_LENGTH = 64*1024;

    @Autowired
    private OSSClient client;
    @Autowired
    private AesService aesService;
    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.bucketName}")
    private String bucketName;

    /**
     * 从oss下载一个文件片段
     * @param request
     * @param ctx
     * @return
     */
    public boolean downloadFromOss(DownloadRequest request, ChannelHandlerContext ctx) {

        // TODO 应该根据一个key去oss下载，这里写死了，因为没有需求不知道怎么定义key
        String key = "fd.pdf";
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        getObjectRequest.setRange(request.getStart(), -1);
        OSSObject ossObject = client.getObject(getObjectRequest);
        try(InputStream in = ossObject.getObjectContent()) {
            long startTime = System.currentTimeMillis();
            byte[] bytes = new byte[BLOCK_LENGTH];
            int len;
            int sum = 0;
            int cnt = 0;
            while ((len = in.read(bytes)) != -1) {
                DownloadResponse downloadResponse = new DownloadResponse();
                byte[] dataCopy = Arrays.copyOf(bytes, len);
                dataCopy = encrypt(dataCopy, request.getUserId());
                downloadResponse.setData(dataCopy);
                downloadResponse.setLength(dataCopy.length);
                downloadResponse.setRequestId(request.getRequestId());
                downloadResponse.setUserId(request.getUserId());
                ctx.writeAndFlush(downloadResponse);
                sum += len;
                cnt++;
            }
            LOGGER.info("加密完成 大小 "+sum/1024.0+"KB 耗时 "+ (System.currentTimeMillis()-startTime) + " " + cnt+" 次大小 "+len);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Boolean.FALSE;
        }
    }

    public byte[] encrypt(byte[] bytes, int userId) throws Exception{
        bytes = aesService.encrypt(bytes, userId);
        return bytes;
    }
}
