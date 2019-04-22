package com.sxkl.project.cloudnote.analyzer.ikanalyzer.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class LexiconCacheManagerFactory {

    @Autowired
    private LexiconCacheManager lexiconCacheManager;
    private static LexiconCacheManager manager;

    @PostConstruct
    private void init() {
        manager = lexiconCacheManager;
    }

    public static LexiconCacheManager getLexiconCacheManager() {
        return manager;
    }
}
