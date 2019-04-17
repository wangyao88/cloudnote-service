package com.sxkl.project.cloudnote.etl.service.transform;

import com.sxkl.project.cloudnote.etl.entity.BaseEntity;
import com.sxkl.project.cloudnote.etl.service.EtlInfo;

import java.util.List;

public class Transform {

    protected EtlInfo etlInfo;

    public Transform(EtlInfo etlInfo) {
        this.etlInfo = etlInfo;
    }

    public List<? extends BaseEntity> transform(List<? extends BaseEntity> entities) {
        etlInfo.setTransformStart(System.currentTimeMillis());
        etlInfo.setTransformEnd(System.currentTimeMillis());
        return entities;
    }
}
