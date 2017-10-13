package com.fengdui.test.xhoa.framework.web;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Wander.Zeng
 * @data 2015-8-5 上午9:35:33
 * @desc PageReqData.java
 */
public class PageReqData {

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
