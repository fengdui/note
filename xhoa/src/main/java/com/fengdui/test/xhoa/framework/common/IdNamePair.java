package com.fengdui.oa.framework.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Wander.Zeng
 * @data 2015-9-24 上午9:52:22
 * @desc IdNamePair.java
 */
public class IdNamePair {

	private Integer id;
	private String name;

	public IdNamePair() {

	}

	public IdNamePair(String name) {
		this.name = name;
	}

	public IdNamePair(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
