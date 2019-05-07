package com.sxkl.project.cloudnote.attendance.controller;

import com.sxkl.project.cloudnote.attendance.entity.StandardWorkDateTime;
import com.sxkl.project.cloudnote.attendance.service.StandardWorkDateTimeService;
import com.sxkl.project.cloudnote.base.controller.BaseController;
import com.sxkl.project.cloudnote.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/standardWorkDateTime")
public class StandardWorkDateTimeController extends BaseController<StandardWorkDateTime> {

    @Autowired
    private StandardWorkDateTimeService standardWorkDateTimeService;

    @Override
    protected BaseService<StandardWorkDateTime> getBaseService() {
        return standardWorkDateTimeService;
    }
}
