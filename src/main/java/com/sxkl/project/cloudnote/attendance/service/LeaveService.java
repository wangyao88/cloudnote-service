package com.sxkl.project.cloudnote.attendance.service;

import com.sxkl.project.cloudnote.attendance.entity.Event;
import com.sxkl.project.cloudnote.attendance.entity.Leave;
import com.sxkl.project.cloudnote.attendance.mapper.LeaveMapper;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveService extends BaseService<Leave> {

    @Autowired
    private LeaveMapper leaveMapper;

    @Override
    protected BaseMapper<Leave> getMapper() {
        return leaveMapper;
    }

    public List<Event> getLeaveEvents(Event event, String userId) {
        Leave condition = new Leave();
        condition.setUserId(userId);
        condition.setStartDate(event.getStartDateTime());
        condition.setEndDate(event.getEndDateTime());
        List<Leave> leaves = leaveMapper.findByCondition(condition);
        return leaves.stream().map(leave -> {
            LocalDateTime start = leave.getLeaveStart();
            LocalDateTime end = leave.getLeaveEnd();
            return AttendanceServiceHelper.initEvent(leave.getId(), AttendanceServiceHelper.LEAVE, start, end);
        }).collect(Collectors.toList());
    }
}
