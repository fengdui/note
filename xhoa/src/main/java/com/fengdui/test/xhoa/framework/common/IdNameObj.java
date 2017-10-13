package com.fengdui.test.xhoa.framework.common;
/**
 * @author fd
 * @date 2015年10月16日
 * @desc IdNameObj.java
 */
public class IdNameObj {
	private Object id;
	private Object name;
	
	public IdNameObj() {
		
	}
	public IdNameObj(Object id, Object name) {
		this.id = id;
		this.name = name;
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public Object getName() {
		return name;
	}
	public void setName(Object name) {
		this.name = name;
	}
	
}
