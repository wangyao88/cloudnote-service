package com.sxkl.project.cloudnote.todo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {

    private String id;
    private String title;
    private String url;
    private LocalDateTime start;
    private LocalDateTime end;
    private boolean allDay;
    private String backgroundColor;
    private String textColor;
}
