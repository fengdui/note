package com.mycat.test.controller;

import com.base.controller.BasicController;
import com.mycat.test.model.User;
import com.mycat.test.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;

import com.base.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController extends BasicController{

    @Resource(name="userService")
    private IUserService userService;

    @RequestMapping(value = "/")
    public ModelAndView list(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/user/list");
        String ope_result = request.getParameter(OPE_RESULT);
        if(org.apache.commons.lang.StringUtils.isNotBlank(ope_result)){
            mv.addObject(OPE_RESULT,ope_result + "," + System.currentTimeMillis());
        }

        try{
            List<User> users = this.userService.getAll();
            mv.addObject("users", users);
        }catch (ServiceException e){
            logger.error("查询操作失败",e);
        }
        return mv;
    }

    @RequestMapping(value = "/toAdd")
    public ModelAndView toAdd(Long userId){
        ModelAndView mv = new ModelAndView("/user/add");
        return mv;
    }

    @RequestMapping(value = "/add")
    public RedirectView add(User user, HttpServletRequest request){
        RedirectView rv = new RedirectView("/");
        String result = RESULT_ERROR;
        try{
            userService.create(user);
            result = RESULT_ADD_SUCCESS;
        }catch (ServiceException e){
            logger.error("添加信息失败",e);
        }
        rv.addStaticAttribute(OPE_RESULT, result);
        return rv;
    }

    @RequestMapping(value = "/toEdit")
    public ModelAndView toEdit(Long id){
        ModelAndView mv = new ModelAndView("/user/edit");
        try{
            User user = userService.getObject(id);
            mv.addObject("user", user);
        }catch (ServiceException e){
            logger.error("不存在要修改的信息",e);
        }
        return mv;
    }

    @RequestMapping(value = "/edit")
    public RedirectView edit(User employee){
        RedirectView rv = new RedirectView("/");
        String result = RESULT_ERROR;
        try{
            userService.update(employee);
            result = RESULT_EDIT_SUCCESS;
        }catch (ServiceException e){
            logger.error("修改信息失败",e);
        }
        rv.addStaticAttribute(OPE_RESULT,result);
        return rv;
    }

    @RequestMapping(value = "/view")
    public ModelAndView view(Long id){
        ModelAndView mv = new ModelAndView("/user/view");
        try{
            User user = userService.getObject(id);
            mv.addObject("user", user);
        }catch (ServiceException e){
            logger.error("不存在的信息",e);
        }
        return mv;
    }

    @RequestMapping(value = "/delete")
    public RedirectView delete(Long userId){
        RedirectView rv = new RedirectView("/");
        String result = RESULT_ERROR;
        try{
            userService.delete(userId);
            result = RESULT_EDIT_SUCCESS;
        }catch (ServiceException e){
            logger.error("删除信息失败",e);
        }
        rv.addStaticAttribute(OPE_RESULT,result);
        return rv;
    }

}
