package com.sxkl.project.cloudnote.company.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CompanyNote {

    private String id;
    private String title;
    private String content;
    private Date createDate;
    private String companyId;
    private String userId;
}
