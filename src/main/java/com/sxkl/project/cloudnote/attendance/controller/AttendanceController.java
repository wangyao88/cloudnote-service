package com.sxkl.project.cloudnote.attendance.controller;

import com.google.common.collect.Lists;
import com.sxkl.project.cloudnote.attendance.entity.Attendance;
import com.sxkl.project.cloudnote.attendance.entity.Event;
import com.sxkl.project.cloudnote.attendance.entity.StandardWorkDateTime;
import com.sxkl.project.cloudnote.attendance.service.AttendanceService;
import com.sxkl.project.cloudnote.attendance.service.StandardWorkDateTimeService;
import com.sxkl.project.cloudnote.base.controller.BaseController;
import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.etl.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/attendance")
public class AttendanceController extends BaseController<Attendance> {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private StandardWorkDateTimeService standardWorkDateTimeService;

    @Override
    protected BaseService<Attendance> getBaseService() {
        return attendanceService;
    }

    @PostMapping("/findCalendarEvents")
    @ResponseBody
    public List<Event> findCalendarEvents(@RequestBody Event event, HttpServletRequest request) {
        String userId = getUserId(request);
        return attendanceService.findEvents(event, userId);
    }

    @GetMapping("/calendarPage")
    public String calendarPage() {
        return getViewName("calendar");
    }

    @Override
    protected OperateResult add(Attendance attendance, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = getUserId(request);
        OperateResult operateResult = attendanceService.check(userId);
        if(operateResult.isStatus()) {
            return super.add(attendance, request, response);
        }
        return operateResult;
    }
}
