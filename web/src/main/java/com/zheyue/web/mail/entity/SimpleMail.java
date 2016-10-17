package com.zheyue.web.mail.entity;

import java.io.Serializable;

/**
 * Created by fd on 2016/9/14.
 */
public class SimpleMail implements Serializable {

    // 邮件的From域
    private String fromAddress = "";

    // 邮件的to域
    private String toAddress = "";

    // 邮件的标题
    private String subject = "";

    // 邮件的正文
    private String text = "";

    private int isMime = 0;

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIsMime() {
        return isMime;
    }

    public void setIsMime(int isMime) {
        this.isMime = isMime;
    }
}