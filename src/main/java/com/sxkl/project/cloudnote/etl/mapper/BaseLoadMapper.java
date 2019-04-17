package com.sxkl.project.cloudnote.etl.mapper;

import com.sxkl.project.cloudnote.etl.entity.BaseEntity;

public interface BaseLoadMapper<Entity extends BaseEntity> {

    void save(Entity entity);

    void delete();
}
