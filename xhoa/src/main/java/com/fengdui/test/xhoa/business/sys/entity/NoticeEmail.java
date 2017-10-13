package com.fengdui.oa.business.sys.entity;

import com.fengdui.oa.framework.orm.EntityBase;
import com.fengdui.oa.framework.util.date.DateUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * @author Wander.Zeng
 * @data 2015-7-16 上午11:31:15
 * @desc NoticeEmail.java
 */
public class NoticeEmail extends EntityBase {

	private static final long serialVersionUID = -5461342384582815136L;

	private String icon;
	private String sender;
	private String title;
	private String content;
	private Date sendDate;

	public NoticeEmail() {

	}

	public NoticeEmail(String icon, String sender, String title, String content, Date sendDate) {
		this.icon = icon;
		this.sender = sender;
		this.title = title;
		this.content = content;
		this.sendDate = sendDate;
	}

	public String getSendDateInterval() {
		String sendDateInterval = "";
		if (null != sendDate) {
			int secInterval = DateUtil.dateInterval4Sec(sendDate, DateUtil.currentDate());
			if (secInterval < 60) {
				sendDateInterval = "0分钟前";
			} else if (secInterval < 60 * 60) {
				sendDateInterval = secInterval / 60 + "分钟前";
			} else if (secInterval < 60 * 60 * 24) {
				sendDateInterval = secInterval / 60 / 60 + "小时前";
			} else {
				sendDateInterval = secInterval / 60 / 60 / 24 + "天前";
			}
		}
		return sendDateInterval;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
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

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
