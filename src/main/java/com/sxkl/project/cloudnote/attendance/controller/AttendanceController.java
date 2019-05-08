package com.sxkl.project.cloudnote.attendance.controller;

import com.sxkl.project.cloudnote.attendance.entity.Attendance;
import com.sxkl.project.cloudnote.attendance.entity.Event;
import com.sxkl.project.cloudnote.attendance.service.AttendanceService;
import com.sxkl.project.cloudnote.base.controller.BaseController;
import com.sxkl.project.cloudnote.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/attendance")
public class AttendanceController extends BaseController<Attendance> {

    @Autowired
    private AttendanceService attendanceService;

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
}
