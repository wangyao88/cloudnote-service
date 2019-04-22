package com.sxkl.project.cloudnote.analyzer.ikanalyzer.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import com.sxkl.project.cloudnote.etl.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
public class LexiconCacheManager {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private LexiconMapper mapper;

    private Cache<String, Set<String>> cache = CacheBuilder.newBuilder().build();

    public Set<String> getLexiconsFromCache(String key) {
        Set<String> datas;
        try {
            datas = cache.get(key, ()-> getLexiconsFromRedis(key));
        } catch (ExecutionException e) {
            e.printStackTrace();
            datas = Sets.newHashSet();
        }
        return datas;
    }

    private Set<String> getLexiconsFromRedis(String key) {
        Set<String> members = redisTemplate.opsForSet().members(key);
        if(members.isEmpty()) {
            synchronized(this) {
                List<String> datas = mapper.getLexicons(key);
                if(!datas.isEmpty()) {
                    redisTemplate.opsForSet().add(key, datas.stream().toArray(String[]::new));
                    return new HashSet<>(datas);
                }
            }
        }
        return members;
    }

    public void addLexicon(String key, String lexicon) {
        Set<String> datas = Sets.newHashSet(getLexiconsFromCache(key));
        datas.add(lexicon);
        cache.put(key, datas);
        redisTemplate.opsForSet().intersect(key, lexicon);
        mapper.addLexicon(UUIDUtil.getUUID(), key, lexicon);
    }

    public void deleteLexicon(String key, String lexicon) {
        Set<String> lexiconsFromCache = getLexiconsFromCache(key);
        lexiconsFromCache.remove(lexicon);
        cache.put(key, lexiconsFromCache);
        redisTemplate.opsForSet().remove(key, lexicon);
        mapper.deleteLexicon(lexicon);
    }
}
