package com.sxkl.project.cloudnote.etl.entity;


import lombok.Data;

import java.util.Date;

@Data
public class Tally extends BaseEntity {

    private static final long serialVersionUID = -2027506479068347392L;

    private String id;
    private float money;
    private String mark;
    private String categoryId;
    private Date createDate;
    private String accountBookId;
    private String type;
    private String userId;

}
