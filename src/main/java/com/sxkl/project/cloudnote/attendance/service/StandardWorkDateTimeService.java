package com.sxkl.project.cloudnote.attendance.service;

import com.sxkl.project.cloudnote.attendance.entity.StandardWorkDateTime;
import com.sxkl.project.cloudnote.attendance.mapper.StandardWorkDateTimeMapper;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StandardWorkDateTimeService extends BaseService<StandardWorkDateTime> {

    @Autowired
    private StandardWorkDateTimeMapper standardWorkDateTimeMapper;

    @Override
    protected BaseMapper<StandardWorkDateTime> getMapper() {
        return standardWorkDateTimeMapper;
    }

    public LocalDateTime getAmStart(String userId, LocalDate localDate) {
        StandardWorkDateTime standardWorkDateTime = getStandardWorkDateTime(userId);
        String amStart = standardWorkDateTime.getAmStart();
        String[] split = amStart.split(":");
        return localDate.atTime(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    public LocalDateTime getAmEnd(String userId, LocalDate localDate) {
        StandardWorkDateTime standardWorkDateTime = getStandardWorkDateTime(userId);
        String amEnd = standardWorkDateTime.getAmEnd();
        String[] split = amEnd.split(":");
        return localDate.atTime(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    public LocalDateTime getPmStart(String userId, LocalDate localDate) {
        StandardWorkDateTime standardWorkDateTime = getStandardWorkDateTime(userId);
        String pmStart = standardWorkDateTime.getPmStart();
        String[] split = pmStart.split(":");
        return localDate.atTime(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    public LocalDateTime getPmEnd(String userId, LocalDate localDate) {
        StandardWorkDateTime standardWorkDateTime = getStandardWorkDateTime(userId);
        String pmEnd = standardWorkDateTime.getPmEnd();
        String[] split = pmEnd.split(":");
        return localDate.atTime(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    public StandardWorkDateTime getStandardWorkDateTime(String userId) {
        StandardWorkDateTime standardWorkDateTime = new StandardWorkDateTime();
        standardWorkDateTime.setActive("生效");
        standardWorkDateTime.setUserId(userId);
        List<StandardWorkDateTime> standardWorkDateTimes = standardWorkDateTimeMapper.findByCondition(standardWorkDateTime);
        if(standardWorkDateTimes.isEmpty()) {
            return null;
        }
        return standardWorkDateTimes.get(0);
    }
}
