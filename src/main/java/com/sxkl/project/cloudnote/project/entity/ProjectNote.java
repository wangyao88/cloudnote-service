package com.sxkl.project.cloudnote.project.entity;

import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import lombok.Data;

@Data
public class ProjectNote extends BaseEntity {

    private String title;
    private String content;
    private String projectName;
    private String projectId;
}
