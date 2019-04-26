package com.sxkl.project.cloudnote.base.entity;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class BasePageInfo<BaseEntity> {

    private long recordsTotal;
    private int page;
    private int length;
    private int pages;
    private int start;
    private int end;
    private long recordsFiltered;
    private List<BaseEntity> data;

    public BasePageInfo(PageInfo<BaseEntity> pageInfo) {
        this.recordsTotal = pageInfo.getTotal();
        this.data = pageInfo.getList();
        this.pages = pageInfo.getPages();
        this.start = pageInfo.getStartRow();
        this.end = pageInfo.getEndRow();
        this.page = pageInfo.getPageNum();
        this.length = pageInfo.getPageSize();
        this.recordsFiltered = pageInfo.getTotal();
    }
}
