package com.fengdui.oa.framework.orm;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Wander.Zeng
 * @data 2015-6-17 上午10:25:59
 * @desc MultiDataSourceAnnotation.java
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MultiDataSourceAnnotation {

	String key() default "";

}
