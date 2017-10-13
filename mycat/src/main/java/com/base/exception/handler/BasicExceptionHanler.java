package com.base.exception.handler;

import com.base.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: qufengfu
 * Date: 13-7-7
 */
public class BasicExceptionHanler implements HandlerExceptionResolver {
    private transient Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({ServiceException.class})
    public String exception(ServiceException e){
        return "error";
    }

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        return new ModelAndView("error");
    }
}