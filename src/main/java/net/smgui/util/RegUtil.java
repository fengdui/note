package net.smgui.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtil {

    public static String regFind(String text, String reg) {
        Matcher m = Pattern.compile(reg).matcher(text);
        return m.find() ? m.group(1) : "";
    }
    public static String regFind2(String text, String reg) {
        Matcher m = Pattern.compile(reg).matcher(text);
        m.find();
        return m.find() ? m.group(1) : "";
    }
    public static String regFind3(String text, String reg) {
        Matcher m = Pattern.compile(reg).matcher(text);
        m.find();
        m.find();
        return m.find() ? m.group(1) : "";
    }
}
