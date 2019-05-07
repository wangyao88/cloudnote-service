package com.sxkl.project.cloudnote.attendance.service;

import com.sxkl.project.cloudnote.attendance.entity.Attendance;
import com.sxkl.project.cloudnote.attendance.mapper.AttendanceMapper;
import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AttendanceService extends BaseService<Attendance> {

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Override
    protected BaseMapper<Attendance> getMapper() {
        return attendanceMapper;
    }

    @Override
    public OperateResult add(Attendance attendance) throws Exception {
        setDate(attendance);
        LocalDateTime attendanceDate = attendance.getAttendanceDate();
        return super.add(attendance);
    }

    private void setDate(Attendance attendance) {
        switch (attendance.getAttendanceType()) {
            case "上午上班":
                attendance.setAmStart(attendance.getAttendanceDate());
                break;
            case "上午下班":
                attendance.setAmEnd(attendance.getAttendanceDate());
                break;
            case "下午上班":
                attendance.setPmStart(attendance.getAttendanceDate());
                break;
            case "下午下班":
                attendance.setPmEnd(attendance.getAttendanceDate());
                break;
            default:
                break;
        }
    }
}
