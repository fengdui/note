package com.mycat.test.controller;

import com.base.controller.BasicController;
import com.mycat.test.model.Test;
import com.mycat.test.service.ITestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;

import com.base.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

/**
* @ClassName: TestController
* @Description: test
* @author: suxl
* @date:2015-01-19 16:40:14
*/
@Controller
@RequestMapping("/test")
public class TestController extends BasicController{
    @Resource(name="testService")
    private ITestService testService;

    @RequestMapping(value = "/")
    public ModelAndView list(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/test/list");
        String ope_result = request.getParameter(OPE_RESULT);
        if(org.apache.commons.lang.StringUtils.isNotBlank(ope_result)){
            mv.addObject(OPE_RESULT,ope_result + "," + System.currentTimeMillis());
        }

        try{
            List<Test> tests = this.testService.getAll();
            mv.addObject("tests",tests);
        }catch (ServiceException e){
            logger.error("查询操作失败",e);
        }
        return mv;
    }

    @RequestMapping(value = "/toAdd")
    public ModelAndView toAdd(Long userId){
        ModelAndView mv = new ModelAndView("/test/add");
        return mv;
    }

    @RequestMapping(value = "/add")
    public RedirectView add(Test test,HttpServletRequest request){
        RedirectView rv = new RedirectView("/test/");
        String result = RESULT_ERROR;
        try{
            testService.create(test);
            result = RESULT_ADD_SUCCESS;
        }catch (ServiceException e){
            logger.error("添加信息失败",e);
        }
        rv.addStaticAttribute(OPE_RESULT, result);
        return rv;
    }

    @RequestMapping(value = "/toEdit")
    public ModelAndView toEdit(Long id){
        ModelAndView mv = new ModelAndView("/test/edit");
        try{
            Test test = testService.getObject(id);
            mv.addObject("test",test);
        }catch (ServiceException e){
            logger.error("不存在要修改的信息",e);
        }
        return mv;
    }

    @RequestMapping(value = "/edit")
    public RedirectView edit(Test employee){
        RedirectView rv = new RedirectView("/test/");
        String result = RESULT_ERROR;
        try{
            testService.update(employee);
            result = RESULT_EDIT_SUCCESS;
        }catch (ServiceException e){
            logger.error("修改信息失败",e);
        }
        rv.addStaticAttribute(OPE_RESULT,result);
        return rv;
    }

    @RequestMapping(value = "/view")
    public ModelAndView view(Long id){
        ModelAndView mv = new ModelAndView("/test/view");
        try{
            Test test = testService.getObject(id);
            mv.addObject("test",test);
        }catch (ServiceException e){
            logger.error("不存在的信息",e);
        }
        return mv;
    }

    @RequestMapping(value = "/delete")
    public RedirectView delete(Long id){
        RedirectView rv = new RedirectView("/test/");
        String result = RESULT_ERROR;
        try{
            testService.delete(id);
            result = RESULT_EDIT_SUCCESS;
        }catch (ServiceException e){
            logger.error("删除信息失败",e);
        }
        rv.addStaticAttribute(OPE_RESULT,result);
        return rv;
    }

}
