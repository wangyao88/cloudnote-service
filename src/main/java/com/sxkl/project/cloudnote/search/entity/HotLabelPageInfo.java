package com.sxkl.project.cloudnote.search.entity;

import lombok.Data;

import java.util.List;

@Data
public class HotLabelPageInfo {

    private long recordsTotal;
    private int page;
    private int length;
    private long pages;
    private int start;
    private int end;
    private long recordsFiltered;
    private List<HotLabel> data;
}
