package com.sxkl.project.cloudnote.attendance.entity;

import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {

    private String id;
    private String type;
    private String title;
    private String url = StringUtils.EMPTY;
    private String start;
    private String end;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private boolean allDay = true;
    private String textColor = "white";
    private String rendering = "background";
    private String color;
}
