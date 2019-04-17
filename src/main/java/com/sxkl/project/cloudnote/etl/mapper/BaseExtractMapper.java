package com.sxkl.project.cloudnote.etl.mapper;

import com.sxkl.project.cloudnote.etl.entity.BaseEntity;

import java.util.List;

public interface BaseExtractMapper {

    List<? extends BaseEntity> findAll();
}
