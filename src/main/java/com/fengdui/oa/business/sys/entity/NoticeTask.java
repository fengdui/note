package com.fengdui.oa.business.sys.entity;

import com.fengdui.oa.framework.orm.EntityBase;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Wander.Zeng
 * @data 2015-7-16 下午3:37:11
 * @desc NoticeTask.java
 */
public class NoticeTask extends EntityBase {

	private static final long serialVersionUID = 4776919795167063793L;

	private String name;
	private float progress;

	public NoticeTask() {

	}

	public NoticeTask(String name, float progress) {
		this.name = name;
		this.progress = progress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
