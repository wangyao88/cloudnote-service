package com.sxkl.project.cloudnote.user.controller;

import com.sxkl.project.cloudnote.base.controller.BaseController;
import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.user.entity.User;
import com.sxkl.project.cloudnote.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User> {

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

    @GetMapping("/updatePasswordPage")
    public ModelAndView updatePasswordPage(@RequestParam("id") String id) {
        ModelAndView mv = new ModelAndView("user/update");
        mv.addObject("id", id);
        return mv;
    }

    @PostMapping("checkOldPassword")
    @ResponseBody
    public OperateResult checkOldPassword(@RequestParam("id") String id, @RequestParam("password") String password) {
        return userService.checkOldPassword(id, password);
    }

    @PostMapping("updatePassword")
    @ResponseBody
    public OperateResult updatePassword(@RequestParam("id") String id, @RequestParam("password") String password) {
        return userService.updatePassword(id, password);
    }

    @GetMapping("/getPublicKey")
    @ResponseBody
    public OperateResult getPublicKey() {
        return userService.getPublicKey();
    }
}
