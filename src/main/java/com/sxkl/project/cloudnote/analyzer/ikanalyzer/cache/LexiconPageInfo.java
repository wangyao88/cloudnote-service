package com.sxkl.project.cloudnote.analyzer.ikanalyzer.cache;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
import java.util.stream.IntStream;

@Data
public class LexiconPageInfo {

    private long recordsTotal;
    private int page;
    private int length;
    private int pages;
    private int start;
    private int end;
    private long recordsFiltered;
    private List<Lexicon> data;

    public LexiconPageInfo(PageInfo pageInfo, String discriminator) {
        this.recordsTotal = pageInfo.getTotal();
        List list = pageInfo.getList();
        List<Lexicon> lexicons = Lists.newArrayListWithCapacity(list.size());
        IntStream.range(0, list.size()).forEach(num->{
            String id = (num+1)+"";
            lexicons.add(new Lexicon(id, list.get(num).toString(), discriminator));
        });
        this.data = lexicons;
        this.pages = pageInfo.getPages();
        this.start = pageInfo.getStartRow();
        this.end = pageInfo.getEndRow();
        this.page = pageInfo.getPageNum();
        this.length = pageInfo.getPageSize();
        this.recordsFiltered = pageInfo.getTotal();
    }
}
