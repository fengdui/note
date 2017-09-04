package com.base.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * User: qufengfu
 * Date: 13-7-7
 */
@Controller
public class BasicController {
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    /**
     *通用操作返回错误
     */
    public static String RESULT_ERROR = "error";
    /**
     *通用操作返回成功
     */
    public static String RESULT_SUCCESS = "success";

    public static String RESULT_ADD_SUCCESS = "add_success";
    public static String RESULT_EDIT_SUCCESS = "edit_success";
    public static String RESULT_DELETE_SUCCESS = "delete_success";

    /**
     * 操作结果常量
     */
    public static String OPE_RESULT = "ope_result";
    /**
     * 操作结果流水号
     */
    public static String OPE_NUMBER = "ope_number";

}
