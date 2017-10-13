package com.fengdui.test.xhoa.framework.orm;

import java.lang.annotation.*;

/**
 * @author Wander.Zeng
 * @data 2015-7-2 下午4:00:43
 * @desc TagUniqueCustom.java
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
/** 走自定义的 isUnique 方法 */
public @interface TagUniqueCustom {

}
