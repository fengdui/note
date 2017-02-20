package com.fengdui.oa.business.sys.entity;

import com.fengdui.oa.framework.orm.EntityBase;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Wander.Zeng
 * @data 2015-7-15 下午4:59:27
 * @desc NoticeMessage.java
 */
public class NoticeMessage extends EntityBase {

	private static final long serialVersionUID = 7871741244986890289L;

	private String icon;
	private String title;
	private String content;
	private String type;

	public NoticeMessage() {

	}

	public NoticeMessage(String icon, String title, String content, String type) {
		this.icon = icon;
		this.title = title;
		this.content = content;
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
