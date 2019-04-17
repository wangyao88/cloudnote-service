package com.sxkl.project.cloudnote.etl.entity;


import lombok.Data;

import java.sql.Blob;

@Data
public class Image extends BaseEntity {

    private static final long serialVersionUID = -3334370054608327486L;

    private String id;
    private String alt;
    private String aId;
    private byte[] content;
//    private String content;
    private String name;

}
