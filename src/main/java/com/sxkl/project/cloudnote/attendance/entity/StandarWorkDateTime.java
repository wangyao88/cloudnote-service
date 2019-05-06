package com.sxkl.project.cloudnote.attendance.entity;

import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StandarWorkDateTime extends BaseEntity {

    private LocalDateTime amStart;
    private LocalDateTime amEnd;
    private LocalDateTime pmStart;
    private LocalDateTime pmEnd;
}
