package com.sxkl.project.cloudnote.attendance.service;

import com.sxkl.project.cloudnote.attendance.entity.Attendance;
import com.sxkl.project.cloudnote.attendance.mapper.AttendanceMapper;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService extends BaseService<Attendance> {

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Override
    protected BaseMapper<Attendance> getMapper() {
        return attendanceMapper;
    }
}
