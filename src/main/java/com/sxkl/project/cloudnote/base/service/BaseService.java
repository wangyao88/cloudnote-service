package com.sxkl.project.cloudnote.base.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.sxkl.project.cloudnote.base.aop.Operation;
import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.etl.utils.UUIDUtil;

import java.util.List;
import java.util.stream.IntStream;

public abstract class BaseService<T extends BaseEntity> {

    protected abstract BaseMapper<T> getMapper();

    @Operation
    public OperateResult add(T entity) throws Exception{
        entity.setId(UUIDUtil.getUUID());
        getMapper().add(entity);
        return OperateResult.buildSuccess();
    }

    @Operation
    public OperateResult removeOne(String id) throws Exception{
        getMapper().removeOne(id);
        return OperateResult.buildSuccess();
    }

    @Operation
    public OperateResult removeAll(T entity) throws Exception{
        getMapper().removeAll(entity);
        return OperateResult.buildSuccess();
    }

    @Operation
    public OperateResult update(T entity) throws Exception{
        getMapper().update(entity);
        return OperateResult.buildSuccess();
    }

    public BaseEntity findOne(String id) {
        return getMapper().findOne(id);
    }

    public List<T> findAll() {
        return getMapper().findAll();
    }

    public PageInfo<T> findPage(int pageNum, int pageSize, T entity) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> entities = getMapper().findByCondition(entity);
        return new PageInfo<>(entities);
    }

    public List<T> findByCondition(T entity) {
        List<T> entities = getMapper().findByCondition(entity);
        return addIndex(entities);
    }

    public List<T> addIndex(List<T> entities) {
        int size = entities.size();
        List<T> datas = Lists.newArrayListWithCapacity(size);
        IntStream.range(0, size).forEach(num->{
            T data = entities.get(num);
            data.setIndex(num+1);
            datas.add(data);
        });
        return datas;
    }

}
