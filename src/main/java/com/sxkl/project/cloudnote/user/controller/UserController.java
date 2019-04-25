package com.sxkl.project.cloudnote.user.controller;


import com.sxkl.project.cloudnote.config.WebSecurityConfig;
import com.sxkl.project.cloudnote.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("name") String name,
                          @RequestParam("password") String password,
                          HttpSession session) {
        boolean result = User.login(name, password);
        if(result) {
            session.setAttribute(WebSecurityConfig.SESSION_KEY, User.getName());
            return "redirect:";
        }
        return "redirect:login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:login";
    }
}
