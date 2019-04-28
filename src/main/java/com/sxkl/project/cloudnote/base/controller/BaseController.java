package com.sxkl.project.cloudnote.base.controller;

import com.github.pagehelper.PageInfo;
import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import com.sxkl.project.cloudnote.base.entity.BasePageInfo;
import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import com.sxkl.project.cloudnote.etl.utils.UUIDUtil;
import com.sxkl.project.cloudnote.utils.PaginationHelper;
import com.sxkl.project.cloudnote.utils.RequestUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class BaseController<T extends BaseEntity> {

    protected abstract BaseService<T> getBaseService();

    @GetMapping("/addPage")
    public String addPage() {
        return getViewName("add");
    }

    @PostMapping("/add")
    @ResponseBody
    protected OperateResult add(@RequestBody T entity) throws Exception{
        entity.setId(UUIDUtil.getUUID());
        return getBaseService().add(entity);
    }

    @PostMapping("/removeOne")
    @ResponseBody
    protected OperateResult removeOne(@RequestParam("id") String id) throws Exception{
        return getBaseService().removeOne(id);
    }

    @PostMapping("/removeAll")
    @ResponseBody
    protected OperateResult removeAll(@RequestBody T entity) throws Exception{
        return getBaseService().removeAll(entity);
    }

    @GetMapping("/updatePage")
    public ModelAndView updatePage(@RequestParam("id") String id) {
        String viewName =  getViewName("update");
        ModelAndView mv = new ModelAndView(viewName);
        mv.addObject("id", id);
        return mv;
    }

    @PostMapping("/update")
    @ResponseBody
    protected OperateResult update(@RequestBody T entity) throws Exception{
        if(StringUtils.isBlank(entity.getId())) {
            return OperateResult.builder().status(Boolean.FALSE).msg("更新实体主键为空，无法更新").build();
        }
        return getBaseService().update(entity);
    }

    @GetMapping("/findOne")
    @ResponseBody
    protected BaseEntity findOne(@RequestParam("id") String id) {
        return getBaseService().findOne(id);
    }

    @GetMapping("/findAll")
    @ResponseBody
    protected List<T> findAll() {
        return getBaseService().findAll();
    }

    @GetMapping("/tablePage")
    public String tablePage() {
        return getViewName("table");
    }

    @GetMapping("/findPage")
    @ResponseBody
    protected BasePageInfo<T> findPage(T entity, HttpServletRequest request) {
        entity = RequestUtils.requestToBean(request, entity);
        Pageable pageable = PaginationHelper.buildPageInfo(request);
        PageInfo<T> pageInfo = getBaseService().findPage(pageable.getPageNumber(), pageable.getPageSize(), entity);
        return new BasePageInfo<>(pageInfo);
    }

    private String getEntityName() {
        int index = getBaseService().getClass().getSimpleName().indexOf("Service");
        return getBaseService().getClass().getSimpleName().substring(0, index).toLowerCase();
    }

    private String getViewName(String name) {
        return StringUtils.appendJoinEmpty(getEntityName(), "/", name);
    }
}
