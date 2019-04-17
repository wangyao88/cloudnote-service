package com.sxkl.project.cloudnote.search.service;

import com.sxkl.project.cloudnote.etl.entity.Article;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class SearchService {

    @Autowired
    private ElasticsearchTemplate template;

    public List<Article> search(String words, @PageableDefault(sort = "hitNum", direction = Sort.Direction.DESC) Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(words, "title", "content"))
                .withPageable(pageable)
                .build();
        List<Article> articles = template.queryForList(searchQuery, Article.class);
        List<Article> results = articles.stream().map(article -> {
            article.setContent(StringUtils.EMPTY);
            article.setCreateTime(null);
            article.setNId(StringUtils.EMPTY);
            article.setUId(StringUtils.EMPTY);
            return article;
        }).collect(Collectors.toList());
        return results;
    }
}

