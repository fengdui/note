package com.fengdui.note.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by fd on 2016/7/5.
 */
public final class CodecUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);

    public static String encodeURL(String source) {
        String target = null;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("encode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }
    public static String decodeURL(String source) {
        String target = null;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("decode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
