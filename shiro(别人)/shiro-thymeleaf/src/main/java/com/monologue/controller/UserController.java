package com.monologue.controller;

import com.monologue.entity.User;
import com.monologue.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Monologue_zsj on 2021/3/7 15:22
 * Author：小脸儿红扑扑
 * Description：
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public String register(User user) {

        try {
            userService.register(user);
            return "redirect:/toLogin";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/toRegister";
        }
    }

    @RequestMapping("/login")
    public String login(String username, String password) {

        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
            return "redirect:/toIndex";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("账户错误...");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误...");
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/toLogin";
    }
}
