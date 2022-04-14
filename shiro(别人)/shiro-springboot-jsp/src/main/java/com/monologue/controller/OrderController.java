package com.monologue.controller;

import com.monologue.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Monologue_zsj on 2021/3/7 17:01
 * Author：小脸儿红扑扑
 * Description：
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @RequestMapping("/save")
    //@RequiresRoles("admin")   //需要那个角色
    //@RequiresPermissions("user:*:*")    //用来判断权限字符串
    public String save() {
        //获取主体对象
        //Subject subject = SecurityUtils.getSubject();
        //代码方式
        /*if (subject.hasRole("admin")) {
            System.out.println("保存订单");
        }else {
            System.out.println("权限不足");
        }

        if (subject.isPermitted("user:*:*")) {
            System.out.println("保存订单");
        }else {
            System.out.println("权限不足");
        }*/

        return "redirect:/index.jsp";
    }
}
