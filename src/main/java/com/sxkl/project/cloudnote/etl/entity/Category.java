package com.sxkl.project.cloudnote.etl.entity;


import lombok.Data;

@Data
public class Category extends BaseEntity {

    private static final long serialVersionUID = 929011858763041700L;

    private String id;
    private String name;
    private String type;
    private String accountBookId;
    private String parentId;

}
