package com.fengdui.test.shiro.server.json;

import com.alibaba.fastjson.JSON;

/**
 * Created by fd on 2016/9/9.
 */
public class FastJsonUtil {

    public static String serialize(Object object) {
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
            throw new RuntimeException("serialize session error", e);
        }
    }
    public static Object deserialize(String str) {
        try {
            return JSON.parse(str);
        } catch (Exception e) {
            throw new RuntimeException("deserialize session error", e);
        }
    }
}
