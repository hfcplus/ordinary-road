package com.monologue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Monologue_zsj on 2021/3/7 21:58
 * Author：小脸儿红扑扑
 * Description：
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello world!";
    }

    @GetMapping("/toIndex")
    public String toIndex() {
        return "index";
    }

    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }
    @GetMapping("/toRegister")
    public String toRegister() {
        return "register";
    }


}
