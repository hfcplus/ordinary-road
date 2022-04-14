1.配置Spring Security

```java
package com.example.springbootsecurity.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author: currentdraw
 * @date: 2021/7/29
 * @description:
 */
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    //链式编程
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()  //所有用户都可访问
                .antMatchers("/views/view1/**").hasRole("vip1")
                .antMatchers("/views/view2/**").hasRole("vip2")
                .antMatchers("/views/view3/**").hasRole("vip3");
        // 关闭默认的登录框,启动项目时自动跳转到登录页，不太好
        http.formLogin().disable();

        http.formLogin() //(如果没有下面的配置 没有权限默认到登陆界面)
        .usernameParameter("user") // 自定义登录页面传来的用户名
        .passwordParameter("password") // 自定义登录页面传来的密码
        .loginProcessingUrl("/userLogin");// 自定义登录form表单的 action路径

        //开启注销功能,注销成功，返回首页
        http.logout().logoutSuccessUrl("/");

        http.rememberMe() // 默认登录页面的记住我
        .rememberMeParameter("remember"); // 自定义登录页面的记住我的参数

        http.csrf().disable();// 关闭csrf,跨域攻击
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("vip").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2")
                .and().withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2","vip3")
                .and().withUser("guest").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1");
    }
}

```

2.前端thymelef整合springsecurity

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <meta charset="UTF-8">
    <title>index</title>
</head>
<body>
<!--如果未登录显示登录-->
<div sec:authorize="!isAuthenticated()">
    <div> <a href="/toLogin">登录</a></div>
</div>
<div sec:authorize="isAuthenticated()">
    用户名：
    <span sec:authentication="name"></span>
 <a href="/logout">注销</a>
</div>
<!--如果一登陆显示用户名和注销-->

<div sec:authorize="hasAnyRole('vip1','vip2','vip3')">
    <div> <a href="views/view1/1"> 1.1</a></div>
    <div> <a href="views/view1/2"> 1.2</a></div>
    <div> <a href="views/view1/3"> 1.3</a></div>
</div>
<div sec:authorize="hasAnyRole('vip2','vip3')">
    <div> <a href="views/view2/1"> 2.1</a></div>
    <div> <a href="views/view2/2"> 2.2</a></div>
    <div> <a href="views/view2/3"> 2.3</a></div>
</div>

<div sec:authorize="hasAnyRole('vip3')">
    <div> <a href="views/view3/1"> 3.1</a></div>
    <div> <a href="views/view3/2"> 3.2</a></div>
    <div> <a href="views/view3/3"> 3.3</a></div>
</div>

</body>
</html>
```

