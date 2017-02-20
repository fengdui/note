package com.fengdui.oa.framework.orm;

import java.lang.annotation.*;

/**
 * @author Wander.Zeng
 * @data 2015-7-2 下午4:54:27
 * @desc TagUniqueColumn.java
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface TagUniqueColumn {

	/** 列名 */
	public String colName();

	/** 列描述 */
	public String colDesc();

}
