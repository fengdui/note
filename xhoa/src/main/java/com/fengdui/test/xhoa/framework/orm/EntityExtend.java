package com.fengdui.test.xhoa.framework.orm;


import com.fengdui.test.xhoa.framework.constant.ConstantSession;

/**
 * @author Wander.Zeng
 * @data 2015-7-1 下午5:15:36
 * @desc EntityExtend.java
 */
@SuppressWarnings("serial")
public class EntityExtend extends EntityBase {

	/** 空间ID */
	protected Integer spaceId;

	public Integer getSpaceId() {
		if (null == spaceId) {
			return ConstantSession.getInstance().getSpaceId();
		}
		return spaceId;
	}

	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}

}
