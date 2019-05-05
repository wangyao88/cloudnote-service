package com.sxkl.project.cloudnote.user.controller;


import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.user.LoginInterceptor;
import com.sxkl.project.cloudnote.user.entity.User;
import com.sxkl.project.cloudnote.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        session.removeAttribute(LoginInterceptor.SESSION_KEY);
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public OperateResult doLogin(@RequestParam("name") String name,
                                 @RequestParam("password") String password,
                                 HttpSession session) {

        OperateResult operateResult = userService.login(name, password);
        if(operateResult.isStatus()) {
            User user = (User) operateResult.getData();
            session.setAttribute(LoginInterceptor.SESSION_KEY, user);
            session.setAttribute("name", user.getName());
        }
        return operateResult;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(LoginInterceptor.SESSION_KEY);
        return "redirect:login";
    }
}
