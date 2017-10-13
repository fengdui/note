package com.fengdui.test.xhoa.business.res.entity;

import com.fengdui.test.xhoa.framework.orm.EntityBase;
import com.fengdui.test.xhoa.framework.orm.TagLogicDelete;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Soda.Yang
 * @date 2015-9-22 上午11:02:25
 * @desc AppClass.java
 */
@TagLogicDelete
public class AppClass extends EntityBase {

	private static final long serialVersionUID = 8242627526570254232L;

	private String name;
	private Integer parentId;
	private String parentName;
	private String description;

	public AppClass() {
		super();
	}

	public AppClass(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
