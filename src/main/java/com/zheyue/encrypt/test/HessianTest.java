package com.zheyue.encrypt.test;

import com.zheyue.encrypt.model.DownloadRequest;
import com.zheyue.encrypt.serialize.hessian.HessianSerialize;

import java.io.*;
import java.net.Socket;

/**
 * @author FD
 * @date 2016/12/30
 */
public class HessianTest {
    public void main(String[] args) throws IOException {
        Socket s1 = new Socket("127.0.0.1", 8000);
        OutputStream outputStream = s1.getOutputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream byteArrayOutputStream4Int = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream4Int);
        DownloadRequest downloadRequest = new DownloadRequest();

        HessianSerialize hessianSerialize = new HessianSerialize();
        hessianSerialize.serialize(byteArrayOutputStream, downloadRequest);
        byte[] data = byteArrayOutputStream.toByteArray();
        dataOutputStream.writeInt(data.length);
//        System.out.println(data.length);
        outputStream.write(byteArrayOutputStream4Int.toByteArray());
        outputStream.write(data);

        InputStream inputStream = s1.getInputStream();



        s1.close();
    }
}
