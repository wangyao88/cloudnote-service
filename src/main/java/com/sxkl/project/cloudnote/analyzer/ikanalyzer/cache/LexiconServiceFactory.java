package com.sxkl.project.cloudnote.analyzer.ikanalyzer.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class LexiconServiceFactory {

    @Autowired
    private LexiconService lexiconService;
    private static LexiconService service;

    @PostConstruct
    private void init() {
        service = lexiconService;
    }

    public static LexiconService getLexiconService() {
        return service;
    }
}
