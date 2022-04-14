<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>

<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Index</title>
</head>
<body>
    <h1>系统主页 v1.0</h1>

    <%--主身份信息--%>
    <h1><shiro:principal/></h1>
    <shiro:authenticated>
        认证之后展示的信息
    </shiro:authenticated>

    <shiro:notAuthenticated>
        没有认证之后展示的信息
    </shiro:notAuthenticated>

    <a href="${pageContext.request.contextPath}/user/logout">退出登录</a>

    <ul>
        <shiro:hasAnyRoles name="user,admin">
            <li><a href="">用户管理</a>
                <ul>
                    <shiro:hasPermission name="user:add:*">
                        <li><a href="">insert</a></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="user:delete:*">
                        <li><a href="">delete</a></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="user:update:*">
                        <li><a href="">update</a></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="user:find:*">
                        <li><a href="">select</a></li>
                    </shiro:hasPermission>
                </ul>
            </li>
        </shiro:hasAnyRoles>
        <shiro:hasRole name="admin">
            <li><a href="">商品管理</a></li>
            <li><a href="">订单管理</a></li>
            <li><a href="">物流管理</a></li>
            <li><a href="">系统管理</a></li>
        </shiro:hasRole>
    </ul>
</body>
</html>