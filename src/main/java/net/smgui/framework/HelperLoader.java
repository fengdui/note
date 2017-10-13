package net.smgui.framework;

import net.smgui.framework.helper.BeanHelper;
import net.smgui.framework.helper.ClassHelper;
import net.smgui.framework.helper.ControllerHelper;
import net.smgui.framework.helper.IocHelper;
import net.smgui.framework.util.ClassUtil;

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
