package com.sxkl.project.cloudnote.attendance.controller;

import com.sxkl.project.cloudnote.attendance.entity.Leave;
import com.sxkl.project.cloudnote.attendance.service.LeaveService;
import com.sxkl.project.cloudnote.base.controller.BaseController;
import com.sxkl.project.cloudnote.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/leave")
public class LeaveController extends BaseController<Leave> {

    @Autowired
    private LeaveService leaveService;

    @Override
    protected BaseService<Leave> getBaseService() {
        return leaveService;
    }
}
