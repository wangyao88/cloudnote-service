package com.sxkl.project.cloudnote.company.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Company {

    private String id;
    private String name;
    private String flag;
    private String address;
    private Date inDate;
    private Date outDate;
    private Date createDate;
    private String userId;
}
