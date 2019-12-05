package com.chen.servlet;

import com.chen.service.UserService;
import com.chen.service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    UserService userService = new UserServiceImpl();

    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isLogin = userService.login(username, password);
        System.out.println(isLogin);
        if (isLogin) {
            response.sendRedirect(request.getContextPath()+"/goods/list");
        }else {
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().write("用户名或密码输入错误，请重新输入");
        }

    }



}
