package com.sxkl.project.cloudnote.etl.mapper;

import com.sxkl.project.cloudnote.etl.entity.BaseEntity;

public interface BaseCursorExtractMapper extends BaseExtractMapper{

    <T extends BaseEntity> T findOne(String id);
}
