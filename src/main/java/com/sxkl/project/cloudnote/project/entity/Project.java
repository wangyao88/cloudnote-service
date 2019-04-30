package com.sxkl.project.cloudnote.project.entity;

import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import lombok.Data;

@Data
public class Project extends BaseEntity {

    private String name;
    private String projectDescribe;
}
