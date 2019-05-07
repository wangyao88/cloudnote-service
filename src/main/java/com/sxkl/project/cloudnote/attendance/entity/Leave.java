package com.sxkl.project.cloudnote.attendance.entity;

import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Leave extends BaseEntity {

    private LocalDateTime leaveStart;
    private LocalDateTime leaveEnd;
    /**
     * 事假 病假 婚假 丧假 年假 调休 陪产假
     */
    private String name;
    private String tips;
}
