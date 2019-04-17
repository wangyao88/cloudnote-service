package com.sxkl.project.cloudnote.etl.entity;


import lombok.Data;

import java.util.Date;

@Data
public class AccountBook extends BaseEntity{

    private static final long serialVersionUID = 211676060379751976L;

    private String id;
    private String name;
    private String mark;
    private Date createDate;
    private String userId;
}
