package net.smgui.framework.bean;

import net.smgui.framework.util.CastUtil;

import java.util.Map;

/**
 * Created by fd on 2016/7/5.
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    public Map<String, Object> getMap() {
        return paramMap;
    }
}
