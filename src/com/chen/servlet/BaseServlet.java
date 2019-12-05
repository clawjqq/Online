package com.chen.servlet;
import java.io.IOException;

import java.lang.reflect.Method;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BaseSerlvet 基础Servlet，相当于是一个小型框架，SpringMVC
 * Servlet 接口：
 * HttpServlet ：  doGet  doPost
 * Git
 */
public class BaseServlet extends GenericServlet{
    //service方法，每调用一次都会执行该方法
    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        System.out.println("我被执行了");
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI(); // /a/goods/add
        System.out.println(uri);
        int index = uri.lastIndexOf("/");
        String methodName= uri.substring(index+1);//add
        Class<?> clazz = this.getClass();  //GoodsSerlvet@5c76d99
        System.out.println(this);
        try {
                          //clazz.getDeclaredMethod 改成这样什么方法都可以拿到，否则只能用public
            Method method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(this, request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}