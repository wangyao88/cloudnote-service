package com.sxkl.project.cloudnote.search.service;

import com.sxkl.project.cloudnote.etl.entity.Article;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
                .withIndices(ESConstant.INDEX)
                .withHighlightFields(new HighlightBuilder.Field("title"), new HighlightBuilder.Field("content"))
                .build();
        List<Article> articles = template.queryForList(searchQuery, Article.class);
        List<Article> results = articles.stream().map(article -> {
            article.setContent(configureContent(article.getContent()));
            article.setCreateTime(null);
            article.setNId(StringUtils.EMPTY);
            article.setUId(StringUtils.EMPTY);
            return article;
        }).collect(Collectors.toList());
        return results;
    }

    public long count(String words) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(words, "title", "content"))
                .withIndices(ESConstant.INDEX)
                .build();
        long count = template.count(searchQuery, Article.class);
        return count;

    }

    private String configureContent(String content) {
        Document contentDoc = Jsoup.parse(content);
        String text = contentDoc.text();
        if(text.length() > 140){
            text = text.substring(0, 140);
        }
        return text;
    }

    public long total() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(multiMatchQuery(words, "title", "content"))
                .withIndices(ESConstant.INDEX)
                .build();
        long total = template.count(searchQuery, Article.class);
        return total;
    }
}

