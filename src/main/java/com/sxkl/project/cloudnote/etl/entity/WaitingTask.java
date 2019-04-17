package com.sxkl.project.cloudnote.etl.entity;


import lombok.Data;

import java.util.Date;

@Data
public class WaitingTask extends BaseEntity {

    private static final long serialVersionUID = -8452694435127976334L;

    private String id;
    private String name;
    private Date createDate;
    private Date expireDate;
    private String taskType;
    private String uId;
    private String process;
    private String content;
    private Date beginDate;
}
