package com.sxkl.project.cloudnote.todo.entity;

import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {

    private String id;
    private String title;
    private String url = StringUtils.EMPTY;
    private String start;
    private String end;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private boolean allDay = true;
    private String backgroundColor;
    private String textColor;
}
