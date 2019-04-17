package com.sxkl.project.cloudnote.etl.entity;


import lombok.Data;

@Data
public class Flag extends BaseEntity {

    private static final long serialVersionUID = 8947859733159145727L;

    private String id;
    private String name;
    private String fId;
    private String uId;
}
