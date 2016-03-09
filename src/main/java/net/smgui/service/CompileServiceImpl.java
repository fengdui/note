package net.smgui.service;

import net.smgui.common.Constant;
import net.smgui.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * CompileServiceImpl
 *
 * @author FD
 * @date 2016/3/10 0010
 */
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

    public String compile(String command) throws Exception{
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            try {
                command = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(Constant.PATH_COMMOND) + File.separator + command;
                process = Runtime.getRuntime().exec(command);
            } catch (Exception e1) {
                process = null;
                throw e1;
            }
        }

        InputStream is = null;
        BufferedReader br = null;
        try {
            process.waitFor();
            is = process.getInputStream();
            if (is.available() <= 0) {
                throw new Exception("编译错误");
            }
            StringBuilder sb = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
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

    public String compileJava(File javaSrc) {
        return "";
    }
}
