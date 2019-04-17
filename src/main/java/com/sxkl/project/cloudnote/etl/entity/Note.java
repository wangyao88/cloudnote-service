package com.sxkl.project.cloudnote.etl.entity;


import lombok.Data;

@Data
public class Note extends BaseEntity {

    private static final long serialVersionUID = -1415679761724322498L;

    private String id;
    private String name;
    private String uId;
}
