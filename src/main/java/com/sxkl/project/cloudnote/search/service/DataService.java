package com.sxkl.project.cloudnote.search.service;

import com.google.common.collect.Lists;
import com.sxkl.project.cloudnote.etl.entity.Article;
import com.sxkl.project.cloudnote.etl.mapper.remote.RemoteArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
public class DataService {

    @Autowired
    private Client esClient;
    @Autowired
    private ElasticsearchTemplate template;
    @Autowired
    private RemoteArticleMapper mapper;

    @PostConstruct
    private void init() {
        if(!template.indexExists(ESConstant.INDEX)) {
            template.createIndex(ESConstant.INDEX);
        }
        template.putMapping(Article.class);
    }

    public boolean loadData() {
        try {
            if(template.indexExists(ESConstant.INDEX)) {
                template.deleteIndex(ESConstant.INDEX);
                template.createIndex(ESConstant.INDEX);
                template.putMapping(Article.class);
            }
            List<Article> articles = mapper.findAll();
            articles.forEach(article -> {
                Article temp = mapper.findOne(article.getId());
                IndexQuery indexQuery = new IndexQueryBuilder().withId(temp.getId()).withObject(temp).build();
                template.bulkIndex(Lists.newArrayList(indexQuery));
            });
            return true;
        } catch (Exception e) {
            log.error("insert or update article error.", e);
            return false;
        }
    }

    public boolean insertOrUpdate(Article article) {
        try {
            IndexQuery indexQuery = new IndexQueryBuilder().withId(article.getId()).withObject(article).build();
            template.index(indexQuery);
            return true;
        } catch (Exception e) {
            log.error("insert or update article error.", e);
            return false;
        }
    }

    public boolean delete(String id) {
        try {
            template.delete(Article.class, id);
            return true;
        } catch (Exception e) {
            log.error("delete article error.", e);
            return false;
        }
    }

    /**
     * 检查健康状态
     */
    public boolean ping() {
        try {
            ActionFuture<ClusterHealthResponse> health = esClient.admin().cluster().health(new ClusterHealthRequest());
            ClusterHealthStatus status = health.actionGet().getStatus();
            if (status.value() == ClusterHealthStatus.RED.value()) {
                throw new RuntimeException("elasticsearch cluster health status is red.");
            }
            return true;
        } catch (Exception e) {
            log.error("ping elasticsearch error.", e);
            return false;
        }
    }
}
