package com.sxkl.project.cloudnote.attendance.service;

import com.sxkl.project.cloudnote.attendance.entity.Leave;
import com.sxkl.project.cloudnote.attendance.mapper.LeaveMapper;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveService extends BaseService<Leave> {

    @Autowired
    private LeaveMapper leaveMapper;

    @Override
    protected BaseMapper<Leave> getMapper() {
        return leaveMapper;
    }
}
