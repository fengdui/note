package net.smgui.service;

import net.smgui.common.Constant;
import net.smgui.util.FileUtil;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.util.UUID;


@Service
public class CompileServiceImpl implements CompileService{

    public String getRealPath(String path) {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(path);
    }
    public String importFile(MultipartFile fileSrc) throws Exception{

        StringBuilder filePath = new StringBuilder();
        filePath.append(getRealPath(""));
        filePath.append(UUID.randomUUID());
        filePath.append(fileSrc.getOriginalFilename());
        System.out.println(filePath.toString());
        FileUtil.copy(fileSrc, new File(filePath.toString()));
        return filePath.toString();
    }

    public String savaJava(String javaSrc, String path) {
        FileOutputStream outputStream = null;
        try {
            path = path+File.separator+"Main.java";
            byte[] bytes = javaSrc.getBytes("utf-8");
            outputStream = new FileOutputStream(path);
            outputStream.write(bytes);

            FileInputStream input = new FileInputStream(path);
            BufferedReader bf = new BufferedReader(new InputStreamReader(input, "utf-8"));
            String ss;
            while ((ss = bf.readLine()) != null) {
                System.out.println(ss);
            }
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String compile(String command) throws Exception{
        System.out.println("开始编译"+command);
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (Exception e) {
           return "系统正在维护中";
        }
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = process.getErrorStream();
            StringBuilder sb = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(is, "gbk"));
            String line = null;
            System.out.println("F" + Charset.defaultCharset().name());
            while ((line = br.readLine()) != null) {
                sb.append(line);
                System.out.println(line);
                sb.append("\n");
            }
            process.waitFor();
            return sb.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (null != is) {
                    is.close();
                    is = null;
                }
                if (null != br) {
                    br.close();
                    br = null;
                }
                if (null != process) {
                    process.destroy();
                    process = null;
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public String compileJava(String path) throws Exception {
        String command = "javac -encoding utf-8 "+ path;
        return compile(command);
    }

    public static void main(String[] args) {

    }
}
