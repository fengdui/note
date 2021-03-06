package com.fengdui.test.fileEncrypt.test;

import com.fengdui.test.fileEncrypt.model.DownloadRequest;
import com.fengdui.test.fileEncrypt.serialize.hessian.HessianSerialize;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

/**
 * @author FD
 * @date 2016/12/30
 */
public class HessianTest {
    public void main(String[] args) throws IOException {
//        Socket s1 = new Socket("127.0.0.1", 8000);
        OutputStream outputStream = new FileOutputStream("D:\\jmeter\\hessian.txt");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");
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

//        InputStream inputStream = s1.getInputStream();

        outputStream.close();
//        s1.close();
    }
}
