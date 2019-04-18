package com.sxkl.project.cloudnote.search.api;


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

    @GetMapping("/ping")
    public boolean ping() {
        return dataService.ping();
    }

    @GetMapping("/loadData")
    public boolean loadData() {
        return dataService.loadData();
    }

    @PostMapping("/insertOrUpdate")
    public boolean insertOrUpdate(@RequestBody Article article) {
        return dataService.insertOrUpdate(article);
    }

    @PostMapping("/delete")
    public boolean delete(String id) {
        return dataService.delete(id);
    }
}
