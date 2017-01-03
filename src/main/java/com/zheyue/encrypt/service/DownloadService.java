package com.zheyue.encrypt.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.zheyue.encrypt.model.DownloadRequest;
import com.zheyue.encrypt.model.DownloadResponse;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.Key;
import java.util.UUID;

/**
 * @author FD
 * @date 2017/1/3
 */

@Service
public class DownloadService {

    //默认一块64kb
    private static final int BLOCK_LENGTH = 64*1024;

    @Autowired
    private OSSClient client;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.bucketName}")
    private String bucketName;

    public Boolean downloadFromOss(DownloadRequest request, ChannelHandlerContext ctx) {

        String key = "fd.pdf";
        OSSObject ossObject = client.getObject(bucketName, key);
        try(InputStream in = ossObject.getObjectContent()) {
            long startTime = System.currentTimeMillis();
            byte[] bytes = new byte[BLOCK_LENGTH];
            int len = 0;
            int sum = 0;
            int cnt = 0;
            while ((len = in.read(bytes)) != -1) {
                DownloadResponse downloadResponse = new DownloadResponse();
                downloadResponse.setData(bytes);
                downloadResponse.setLength(len);
                ctx.writeAndFlush(downloadResponse);
                sum += len;
                cnt++;
            }
            System.out.println("加密完成 大小 "+sum/1024.0+"KB 耗时 "+ (System.currentTimeMillis()-startTime) + " " + cnt+" 次大小 "+len);
//            System.out.println("加密完成 大小 "+sum/1024.0/1024+"MB 耗时 "+ (System.currentTimeMillis()-startTime)+"  "+sum);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
