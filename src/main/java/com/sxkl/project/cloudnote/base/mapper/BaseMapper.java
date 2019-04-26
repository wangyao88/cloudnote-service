package com.sxkl.project.cloudnote.base.mapper;

import com.sxkl.project.cloudnote.base.entity.BaseEntity;

import java.util.List;

public interface BaseMapper<T extends BaseEntity> {

    void add(T entity);

    void removeOne(String id);

    void removeAll(T entity);

    void update(T entity);

    T findOne(String id);

    List<T> findAll();

    List<T> findByCondition(T entity);
}
