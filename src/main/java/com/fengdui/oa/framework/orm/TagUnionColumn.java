package com.fengdui.oa.framework.orm;

import java.lang.annotation.*;

/**
 * @author Wander.Zeng
 * @data 2015-8-26 下午3:09:29
 * @desc TagUnionColumn.java
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface TagUnionColumn {

	/** 变量名组合 */
	public String[] fieldNameUnion();

}
