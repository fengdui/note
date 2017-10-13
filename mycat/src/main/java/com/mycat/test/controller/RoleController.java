package com.mycat.test.controller;

import com.base.controller.BasicController;
import com.base.exception.ServiceException;
import com.mycat.test.model.Role;
import com.mycat.test.model.User;
import com.mycat.test.service.IRoleService;
import com.mycat.test.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @ClassName: TestController
* @Description: user
* @author: suxl
* @date:2015-01-19 16:40:14
*/
@Controller
@RequestMapping("/role")
public class RoleController extends BasicController{

    @Resource(name="roleService")
    private IRoleService roleService;

    @RequestMapping(value = "/")
    public ModelAndView list(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/role/list");
        String ope_result = request.getParameter(OPE_RESULT);
        if(org.apache.commons.lang.StringUtils.isNotBlank(ope_result)){
            mv.addObject(OPE_RESULT,ope_result + "," + System.currentTimeMillis());
        }

        try{
            List<Role> roles = this.roleService.getAll();
            mv.addObject("roles", roles);
        }catch (ServiceException e){
            logger.error("查询操作失败",e);
        }
        return mv;
    }

    @RequestMapping(value = "/toAdd")
    public ModelAndView toAdd(Long roleId){
        ModelAndView mv = new ModelAndView("/role/add");
        return mv;
    }

    @RequestMapping(value = "/add")
    public RedirectView add(Role role, HttpServletRequest request){
        RedirectView rv = new RedirectView("/");
        String result = RESULT_ERROR;
        try{
            roleService.create(role);
            result = RESULT_ADD_SUCCESS;
        }catch (ServiceException e){
            logger.error("添加信息失败",e);
        }
        rv.addStaticAttribute(OPE_RESULT, result);
        return rv;
    }

    @RequestMapping(value = "/toEdit")
    public ModelAndView toEdit(Long roleId){
        ModelAndView mv = new ModelAndView("/role/edit");
        try{
            Role role = roleService.getObject(roleId);
            mv.addObject("role", role);
        }catch (ServiceException e){
            logger.error("不存在要修改的信息",e);
        }
        return mv;
    }

    @RequestMapping(value = "/edit")
    public RedirectView edit(Role role){
        RedirectView rv = new RedirectView("/");
        String result = RESULT_ERROR;
        try{
            roleService.update(role);
            result = RESULT_EDIT_SUCCESS;
        }catch (ServiceException e){
            logger.error("修改信息失败",e);
        }
        rv.addStaticAttribute(OPE_RESULT,result);
        return rv;
    }

    @RequestMapping(value = "/view")
    public ModelAndView view(Long id){
        ModelAndView mv = new ModelAndView("/role/view");
        try{
            Role role = roleService.getObject(id);
            mv.addObject("role", role);
        }catch (ServiceException e){
            logger.error("不存在的信息",e);
        }
        return mv;
    }

    @RequestMapping(value = "/delete")
    public RedirectView delete(Long id){
        RedirectView rv = new RedirectView("/");
        String result = RESULT_ERROR;
        try{
            roleService.delete(id);
            result = RESULT_EDIT_SUCCESS;
        }catch (ServiceException e){
            logger.error("删除信息失败",e);
        }
        rv.addStaticAttribute(OPE_RESULT,result);
        return rv;
    }

}
