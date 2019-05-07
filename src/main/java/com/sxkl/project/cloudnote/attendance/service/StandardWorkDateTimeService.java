package com.sxkl.project.cloudnote.attendance.service;

import com.sxkl.project.cloudnote.attendance.entity.StandardWorkDateTime;
import com.sxkl.project.cloudnote.attendance.mapper.StandardWorkDateTimeMapper;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StandardWorkDateTimeService extends BaseService<StandardWorkDateTime> {

    @Autowired
    private StandardWorkDateTimeMapper standardWorkDateTimeMapper;

    @Override
    protected BaseMapper<StandardWorkDateTime> getMapper() {
        return standardWorkDateTimeMapper;
    }
}
