package com.sxkl.project.cloudnote.attendance.controller;

import com.sxkl.project.cloudnote.attendance.entity.Absence;
import com.sxkl.project.cloudnote.attendance.entity.Event;
import com.sxkl.project.cloudnote.attendance.service.AbsenceService;
import com.sxkl.project.cloudnote.base.controller.BaseController;
import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/absence")
public class AbsenceController extends BaseController<Absence> {

    @Autowired
    private AbsenceService absenceService;

    @Override
    protected BaseService<Absence> getBaseService() {
        return absenceService;
    }

    @PostMapping("/initWeekendOfCurrentYear")
    @ResponseBody
    public OperateResult initWeekendOfCurrentYear(HttpServletRequest request) {
        String userId = getUserId(request);
        return absenceService.initWeekendOfCurrentYear(userId);
    }
}
