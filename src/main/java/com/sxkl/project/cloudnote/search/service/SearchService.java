package com.sxkl.project.cloudnote.search.service;

import com.google.common.collect.Lists;
import com.sxkl.project.cloudnote.analyzer.ikanalyzer.cache.LexiconService;
import com.sxkl.project.cloudnote.etl.entity.Article;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import com.sxkl.project.cloudnote.search.entity.HotLabel;
import com.sxkl.project.cloudnote.search.entity.HotLabelPageInfo;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class SearchService {

    private static final String HOT_LABELS_ZSET_KEY_IN_REDIS = "hot_labels";

    @Autowired
    private ElasticsearchTemplate template;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private LexiconService lexiconService;

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
        if(!results.isEmpty()) {
            saveSearchWordsToRedis(words);
        }
        return results;
    }

    private void saveSearchWordsToRedis(String words) {
        List<String> results = lexiconService.analysis(words);
        results.forEach(result -> {
            redisTemplate.opsForZSet().incrementScore(HOT_LABELS_ZSET_KEY_IN_REDIS, result, 1);
        });
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

    public HotLabelPageInfo findPageHotLabels(int pageNum, int pageSize, String search) {
        int start = (pageNum-1) * pageSize + 1;
        int end = pageNum * pageSize;

        int startForZset = start == 0 ? 0 : start - 1;
        int endForZset = end - 1;

        Long total = redisTemplate.opsForZSet().size(HOT_LABELS_ZSET_KEY_IN_REDIS);
        List<HotLabel> hotLabels = getHotLabels(startForZset, endForZset);
        HotLabelPageInfo pageInfo = new HotLabelPageInfo();
        pageInfo.setData(hotLabels);
        pageInfo.setRecordsTotal(total);
        pageInfo.setRecordsFiltered(total);
        pageInfo.setStart(start);
        pageInfo.setEnd(end);
        pageInfo.setLength(pageSize);
        pageInfo.setPage(pageNum);
        pageInfo.setPages(getPages(pageSize, total));
        return pageInfo;
    }

    private long getPages(int pageSize, Long total) {
        long pages = total/pageSize;
        return total % pageSize == 0 ? pages : pages+1;
    }

    private List<HotLabel> getHotLabels(int start, int end) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        byte[] rawKey = str2Bytes(HOT_LABELS_ZSET_KEY_IN_REDIS);
        Set<RedisZSetCommands.Tuple> tuples = redisTemplate.getConnectionFactory().getConnection().zRevRangeWithScores(rawKey, start, end);
        List<RedisZSetCommands.Tuple> lists = new ArrayList<>(tuples);
        int size = lists.size();
        List<HotLabel> hotLabels = Lists.newArrayListWithCapacity(size);
        IntStream.range(0, size).forEach(num->{
            int id = num+1;
            RedisZSetCommands.Tuple tuple = lists.get(num);
            String label = stringRedisSerializer.deserialize(tuple.getValue());
            hotLabels.add(new HotLabel(id, label, tuple.getScore()));
        });
        return hotLabels;
    }

    private byte[] str2Bytes(String key) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        return stringRedisSerializer.serialize(key);
    }

    public void removeLabel(String label) {
        byte[] rawKey = str2Bytes(HOT_LABELS_ZSET_KEY_IN_REDIS);
        byte[] rawValue = str2Bytes(label);
        redisTemplate.getConnectionFactory().getConnection().zRem(rawKey, rawValue);
    }
}

