package com.zheyue.encrypt.utils;

/**
 * @author FD
 * @date 2016/11/29
 */
public enum Result2 {
    //内部错误
    OK(0,"ok"),
    UNEXPECTED_ERROR(1, "服务器错误"),
    COMMAND_FAILURE(2, "命令行调用失败"),

    //用户相关
    USERNAME_NOT_UNIQUE(1001, "用户名不唯一"),
    USERNAME_NOT_EXIST(1002, "用户名不存在"),
    CAPTCHA_NOT_CORRECT(1003, "验证码错误"),
    PASSWORD_ERROR(1004, "密码错误"),
    MOBILE_NOT_UNIQUE(1005, "手机号码已注册"),
    MOBILE_NOT_EXIST(1006, "手机号不存在"),
    CAPTCHA_NOT_EXIST(1007, "验证码已过期"),
    LOGIN_FAILURE(1008, "用户名或密码不正确"),
    REGISTER_INFO_ERROR(1009, "注册信息不符合规范"),



    //样板间 方案相关
    REQUIREMENT_INFO_INCOMPLETE(2001, "样板间信息不完全"),


    //文件相关
    FILE_TRANS_FAIL(3001, "文件格式转换失败"),
    FILE_NOT_EXIST(3002, "文件不存在");


    Result2(int code, String msg) {

    }

}
