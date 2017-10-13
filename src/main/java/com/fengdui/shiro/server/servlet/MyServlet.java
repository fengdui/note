package com.fengdui.shiro.server.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Base64;

/**
 * Created by fd on 2016/10/20.
 */
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println(req.getPathInfo());
//        System.out.println(req.getQueryString());
//        System.out.println(req.getRequestURI());
//        System.out.println(req.getRequestURL());
//        System.out.println(req.getContextPath());
//        System.out.println(req.getServletPath());
//        String url = req.getRequestURI();
//        String str = url.substring(url.lastIndexOf('/')+1);
        System.out.println(URLDecoder.decode(req.getQueryString(), "utf-8"));
        System.out.println(req.getParameter("A"));
        System.out.println(req.getParameter("B"));
        req.setCharacterEncoding("urf-8");
    }
}
