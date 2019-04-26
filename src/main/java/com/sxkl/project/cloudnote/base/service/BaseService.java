package com.sxkl.project.cloudnote.base.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxkl.project.cloudnote.base.aop.Operation;
import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;

import java.util.List;

public abstract class BaseService<BaseEntity> {

    protected abstract BaseMapper<BaseEntity> getMapper();

    @Operation
    public OperateResult add(BaseEntity entity) throws Exception{
        getMapper().add(entity);
        return OperateResult.buildSuccess();
    }

    @Operation
    public OperateResult removeOne(String id) throws Exception{
        getMapper().removeOne(id);
        return OperateResult.buildSuccess();
    }

    @Operation
    public OperateResult removeAll(BaseEntity entity) throws Exception{
        getMapper().removeAll(entity);
        return OperateResult.buildSuccess();
    }

    @Operation
    public OperateResult update(BaseEntity entity) throws Exception{
        getMapper().update(entity);
        return OperateResult.buildSuccess();
    }

    public BaseEntity findOne(String id) {
        return getMapper().findOne(id);
    }

    public List<BaseEntity> findAll() {
        return getMapper().findAll();
    }

    public PageInfo<BaseEntity> findPage(int pageNum, int pageSize, BaseEntity entity) {
        PageHelper.startPage(pageNum, pageSize);
        List<BaseEntity> entities = getMapper().findByCondition(entity);
        return new PageInfo<>(entities);
    }

    public List<BaseEntity> findByCondition(BaseEntity entity) {
        return getMapper().findByCondition(entity);
    }
}
