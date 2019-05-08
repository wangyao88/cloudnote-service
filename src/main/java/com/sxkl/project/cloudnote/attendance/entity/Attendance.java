package com.sxkl.project.cloudnote.attendance.entity;

import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import com.sxkl.project.cloudnote.etl.utils.ObjectUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Attendance extends BaseEntity implements Comparable<Attendance>{

    private LocalDateTime attendanceDate;

    @Override
    public int compareTo(Attendance other) {
        if(ObjectUtils.isNull(this.getAttendanceDate()) || ObjectUtils.isNull(other.getAttendanceDate())) {
            return 0;
        }
        if(this.getAttendanceDate().isBefore(other.getAttendanceDate())) {
            return -1;
        }
        if(this.getAttendanceDate().isAfter(other.getAttendanceDate())) {
            return 1;
        }
        return 0;
    }
}
