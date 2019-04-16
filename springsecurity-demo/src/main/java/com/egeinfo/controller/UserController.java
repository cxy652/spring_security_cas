package com.egeinfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    @RequestMapping("/list.htmls")
    public String list(){
        return "user_list";
    }
}
