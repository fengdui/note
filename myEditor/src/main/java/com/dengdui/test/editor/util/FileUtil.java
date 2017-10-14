package com.dengdui.test.editor.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

/**
 * FileUtil
 *
 * @author FD
 * @date 2016/3/10 0010
 */
public class FileUtil {

    public static void copy(MultipartFile fileSrc, File fileDest) throws Exception {
        File fileDestParent = fileDest.getParentFile();
        if (fileDestParent != null && (!fileDestParent.exists())) {
            fileDestParent.mkdirs();
        }
        fileSrc.transferTo(fileDest);
    }

    /** 计算文件的MD5 */
    public static String getMD5(File file) throws Exception {
        return DigestUtils.md5Hex(new FileInputStream(file));
    }

}