package com.sxkl.project.cloudnote.attendance.entity;

import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import lombok.Data;

@Data
public class StandardWorkDateTime extends BaseEntity {

    private String amStart;
    private String amEnd;
    private String pmStart;
    private String pmEnd;
    private String active;
}
