package com.sxkl.project.cloudnote.base.mapper;

import java.util.List;

public interface BaseMapper<BaseEntity> {

    void add(BaseEntity entity);

    void removeOne(String id);

    void removeAll(BaseEntity entity);

    void update(BaseEntity entity);

    BaseEntity findOne(String id);

    List<BaseEntity> findAll();

    List<BaseEntity> findByCondition(BaseEntity entity);
}
