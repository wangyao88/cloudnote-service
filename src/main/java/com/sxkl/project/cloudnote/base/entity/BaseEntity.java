package com.sxkl.project.cloudnote.base.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    protected String id;
    protected Date createDate;
    protected int index;
    protected String userId;
}
