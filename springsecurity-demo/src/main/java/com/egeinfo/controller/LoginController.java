package com.egeinfo.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/login.htmls")
    public String login(Model model){
        return "login";
    }

    @RequestMapping("/loginFail.htmls")
    public String loginFail(Model model){
        model.addAttribute("msg","登录失败");
        return "login";
    }
}
