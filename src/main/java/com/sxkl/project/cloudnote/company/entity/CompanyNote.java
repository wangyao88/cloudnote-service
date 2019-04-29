package com.sxkl.project.cloudnote.company.entity;

import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class CompanyNote extends BaseEntity {

    private String title;
    private String content;
    private String companyName;
    private String companyId;
}
