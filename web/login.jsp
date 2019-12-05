<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"   prefix="c" %>
<html lang="zh-CN">
<head>
    <title>Login Form Design</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
<body>
    <div class="loginbox">
        <img src="img/D.png" class="avatar">
        <h1>Login</h1>
        <form action="${pageContext.request.contextPath}/user/login" method="post">
            <p>Username</p>
            <input type="text" name="username" placeholder="Enter Username">
            <p>Password</p>
            <input type="password" name="password" placeholder="Enter Password">
            <input type="submit" name="submit" value="Login">

            <a href="#">
                <button type="button" class="btn btn-info" onclick="location.href='${pageContext.request.contextPath}/goods/list'">直接进入</button>
            </a><br>
            <a href="#">Lost your password</a><br>
            <a href="#">Don't have an account</a>
        </form>
    </div>
</body>
</head>
</html>