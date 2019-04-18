package com.egeinfo.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class LoginController {
    private static Log log = LogFactory.getLog(LoginController.class);

    @RequestMapping("/login.html")
    public String login(Model model){
        return "login";
    }
    @RequestMapping(value = "/timeout", method = RequestMethod.GET)
    public String timeout(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.debug("X-Requested-With=" + request.getHeader("X-Requested-With"));
            log.debug("ajax=" + request.getParameter("ajax"));
            if (isAjax(request)) {
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.println("{\"statusCode\":\"301\",\"message\":\"" + "会话超时，请重新登录！" + "\"}");
            } else {
                response.sendRedirect(request.getContextPath() + "/login.html");
            }
        } catch (IOException e) {
            log.error("LoginController.timeout", e);
            return null;
        }
        return null;
    }
    @RequestMapping("/loginFail.html")
    public String loginFail(Model model){
        model.addAttribute("msg","登录失败");
        return "login";
    }
    private boolean isAjax(HttpServletRequest request) {
        if (request != null && ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")) || request.getParameter("ajax") != null))
            return true;
        return false;
    }
}
