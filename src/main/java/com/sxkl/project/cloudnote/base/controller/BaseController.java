package com.sxkl.project.cloudnote.base.controller;

import com.github.pagehelper.PageInfo;
import com.sxkl.project.cloudnote.base.entity.BasePageInfo;
import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.service.BaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<BaseEntity> {

    protected abstract BaseService<BaseEntity> getBaseService();

    @PostMapping("/add")
    @ResponseBody
    protected OperateResult add(@RequestBody BaseEntity entity) throws Exception{
        return getBaseService().add(entity);
    }

    @PostMapping("/removeOne")
    @ResponseBody
    protected OperateResult removeOne(@RequestParam("id") String id) throws Exception{
        return getBaseService().removeOne(id);
    }

    @PostMapping("/removeAll")
    @ResponseBody
    protected OperateResult removeAll(@RequestBody BaseEntity entity) throws Exception{
        return getBaseService().removeAll(entity);
    }

    @PostMapping("/update")
    @ResponseBody
    protected OperateResult update(@RequestBody BaseEntity entity) throws Exception{
        return getBaseService().update(entity);
    }

    @GetMapping("/findOne")
    @ResponseBody
    protected BaseEntity findOne(@RequestParam("id") String id) {
        return getBaseService().findOne(id);
    }

    @GetMapping("/findAll")
    @ResponseBody
    protected List<BaseEntity> findAll() {
        return getBaseService().findAll();
    }

    @GetMapping("/findPage")
    @ResponseBody
    protected BasePageInfo<BaseEntity> findAll(@RequestParam("pageNum") int pageNum,
                                               @RequestParam("pageSize") int pageSize,
                                               @RequestBody BaseEntity entity) {
        PageInfo<BaseEntity> pageInfo = getBaseService().findPage(pageNum, pageSize, entity);
        return new BasePageInfo<>(pageInfo);
    }
}
