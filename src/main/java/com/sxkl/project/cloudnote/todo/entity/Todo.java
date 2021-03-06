package com.sxkl.project.cloudnote.todo.entity;

import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Todo extends BaseEntity {

    private String title;
    private String status;
    private String projectName;
    private String projectId;
    protected LocalDateTime expectedStartDate;
    protected LocalDateTime expectedEndDate;
}
