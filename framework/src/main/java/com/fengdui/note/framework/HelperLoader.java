package com.fengdui.note.framework;


import com.fengdui.note.framework.helper.BeanHelper;
import com.fengdui.note.framework.helper.ClassHelper;
import com.fengdui.note.framework.helper.ControllerHelper;
import com.fengdui.note.framework.helper.IocHelper;
import com.fengdui.note.framework.util.ClassUtil;

/**
 * Created by fd on 2016/7/5.
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
