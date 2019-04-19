package com.sxkl.project.cloudnote.search.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sxkl.project.cloudnote.etl.entity.Article;
import com.sxkl.project.cloudnote.search.service.DataService;
import com.sxkl.project.cloudnote.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/es")
public class SearchApi {

    @Autowired
    private DataService dataService;
    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public List<Article> search(String words, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return searchService.search(words, pageable);
    }

    @GetMapping("/count")
    public long count(String words) {
        return searchService.count(words);
    }

    @GetMapping("/total")
    public long total() {
        return searchService.total();
    }

    @GetMapping("/ping")
    public boolean ping() {
        return dataService.ping();
    }

    @GetMapping("/loadData")
    public boolean loadData() {
        return dataService.loadData();
    }

    @PostMapping("/insertOrUpdate")
    public boolean insertOrUpdate(@RequestBody String articleStr) {
        Article article = convert(articleStr);
        return dataService.insertOrUpdate(article);
    }

    private Article convert(String articleStr) {
        JSONObject json = JSON.parseObject(articleStr);
        Article article = new Article();
        article.setId(json.getString("id"));
        article.setTitle(json.getString("title"));
        article.setContent(json.getString("content"));
        article.setCreateTime(json.getDate("creatTime"));
        article.setHitNum(json.getInteger("hitNum"));
        article.setIsShared(json.getInteger("isSHared"));
        article.setNId(json.getString("nId"));
        article.setUId(json.getString("uId"));
        return article;
    }

    @PostMapping("/delete")
    public boolean delete(String id) {
        System.out.println(id);
        return dataService.delete(id);
    }
}
