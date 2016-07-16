package net.smgui.framework.bean;

import net.smgui.framework.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fd on 2016/7/5.
 */
public class View {

    private String path;

    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<String, Object>();
    }

    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }
    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
