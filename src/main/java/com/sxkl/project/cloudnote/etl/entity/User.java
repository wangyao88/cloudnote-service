package com.sxkl.project.cloudnote.etl.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class User extends BaseEntity {

    private static final long serialVersionUID = -3054483450076417017L;

    private String id;
    private String name;
    private String password;
    private String email;
    private String mailpass;
}
