package com.sxkl.project.cloudnote.company.entity;

import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class Company extends BaseEntity {

    private String name;
    private String flag;
    private String address;
    private Date inDate;
    private Date outDate;
    private String userId;
}
