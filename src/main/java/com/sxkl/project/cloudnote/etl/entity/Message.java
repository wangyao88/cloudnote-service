package com.sxkl.project.cloudnote.etl.entity;


import lombok.Data;

@Data
public class Message extends BaseEntity {

    private static final long serialVersionUID = 5272350984982435800L;

    private String id;
    private String fromId;
    private String fromName;
    private String toId;
    private String text;
    private String date;
}
