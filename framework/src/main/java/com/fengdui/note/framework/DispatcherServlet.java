package com.fengdui.note.framework;


import com.fengdui.note.framework.bean.Data;
import com.fengdui.note.framework.bean.Handler;
import com.fengdui.note.framework.bean.Param;
import com.fengdui.note.framework.bean.View;
import com.fengdui.note.framework.helper.BeanHelper;
import com.fengdui.note.framework.helper.ConfigHelper;
import com.fengdui.note.framework.helper.ControllerHelper;
import com.fengdui.note.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by fd on 2016/7/5.
 */


@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        HelperLoader.init();
        ServletContext servletContext = servletConfig.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.service(req, resp);
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if (StringUtil.isNotEmpty(body)) {
                String[] params = StringUtil.splitString(body, "&");
                if (ArrayUtil.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] array = StringUtil.splitString(param, "=");
                        if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            if (result instanceof View) {
                View view = (View) result;
                String path = view.getPath();
                if (StringUtil.isNotEmpty(path)) {
                    if(path.startsWith("/")) {
                        resp.sendRedirect(req.getContextPath()+path);
                    }
                    else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model
                                .entrySet()) {
                            req.setAttribute(entry.getKey(), entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(req, resp);
                    }
                }
                else if (result instanceof Data) {
                    Data data = (Data) result;
                    Object model = data.getModel();
                    if(model != null) {
                        resp.setContentType("application/json");
                        resp.setCharacterEncoding("UTF-8");
                        PrintWriter writer = resp.getWriter();
                        String json = JsonUtil.toJson(model);
                        writer.write(json);
                        writer.flush();
                        writer.close();
                    }
                }
            }
        }
    }
}
