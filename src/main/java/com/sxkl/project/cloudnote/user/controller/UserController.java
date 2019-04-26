package com.sxkl.project.cloudnote.user.controller;

import com.sxkl.project.cloudnote.base.controller.BaseController;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Override
    protected BaseService getBaseService() {
        return userService;
    }

    @GetMapping("/tablePage")
    public String tablePage() {
        return "user/table";
    }

}
