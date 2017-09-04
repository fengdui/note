package com.base.common;

/**
 * @author suxl
 * @Date 14-05-28
 */
public class Constant {
    public final static String USER_SESSON_KEY = "USER_SESSION";//用户会话key
    public final static String USER_MENU_KEY = "USER_MENU";//用户的菜单列表key

    public final static Integer STATUS_ENABLE = 1;//正常
    public final static Integer STATUS_DISABLE = 0;//禁用

    public final static Integer BOOLEAN_YES = 1;
    public final static Integer BOOLEAN_NO = 0;

    public final static Integer PAGE_SIZE = 10;//分页中每页最大数据量

    public final static String ROLE_PATIENT = "patient";
    public final static String ROLE_DOCTOR = "doctor";
    public final static String ROLE_NURSE = "nurse";
    public final static String ROLE_ADMIN = "hospital_admin";
    public final static String ROLE_MONITOR_ADMIN = "monitor_admin";

    public final static String URL_KEY = "f5253d3e727685745c59b697babcc080"; // url加密key

    public final static String LOGS_FUNCTION_LOGIN = "user_login"; //日志记录登录方法名

}
