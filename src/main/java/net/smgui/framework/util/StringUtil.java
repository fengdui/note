package net.smgui.framework.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by fd on 2016/7/4.
 */
public class StringUtil {

    public static final String SEPARATOR = String.valueOf((char)29);

    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(final String str) {
        return !isEmpty(str);
    }

    public static String[] splitString(String str, String sp) {
        return str.split("sp");
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream) {
        try {
            int length;
            byte[] buffer = new byte[4*1024];
            while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, buffer.length);
            }
            outputStream.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (Exception e) {

            }
        }
    }

    public static void main(String[] args) {
        System.out.println("XX"+SEPARATOR+"XX");
    }


}
