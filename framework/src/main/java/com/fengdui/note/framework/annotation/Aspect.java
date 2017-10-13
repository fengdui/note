package com.fengdui.note.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by fd on 2016/7/14.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
