package com.sxkl.project.cloudnote.analyzer.ikanalyzer.cache;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import com.sxkl.project.cloudnote.etl.utils.UUIDUtil;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class LexiconService {

    private static final String MAIN_LEXICONS_KEY = "main_lexicons";
    private static final String QUANTIFIER_LEXICONS_KEY = "quantifier_lexicons";
    private static final String EXT_LEXICONS_KEY = "ext_lexicons";
    private static final String STOP_LEXICONS_KEY = "stop_lexicons";

    private static final String PATH_DIC_MAIN = "ikanalyzer/main2012.dic";
    private static final String PATH_DIC_QUANTIFIER = "ikanalyzer/quantifier.dic";
    private static final int INITIALARRAYSIZE_DIC_MAIN = 275713;
    private static final int INITIALARRAYSIZE_DIC_QUANTIFIER = 316;
    private static final int BATCH_SIZE = 1000;

    private static List<KeyValue> kvs = Lists.newArrayList();

    @Autowired
    private LexiconMapper mapper;
    @Autowired
    private LexiconCacheManager lexiconCacheManager;
    @Autowired
    @Qualifier("lexiconSqlSessionTemplate")
    private SqlSessionTemplate lexiconSqlSessionTemplate;

    @PostConstruct
    private void init() {
        kvs.add(new KeyValue(MAIN_LEXICONS_KEY, "主词库"));
        kvs.add(new KeyValue(QUANTIFIER_LEXICONS_KEY, "量词库"));
        kvs.add(new KeyValue(EXT_LEXICONS_KEY, "扩展词库"));
        kvs.add(new KeyValue(STOP_LEXICONS_KEY, "停用词库"));
    }

    @Data
    @AllArgsConstructor
    class KeyValue{
        private String key;
        private String value;
    }

    //以下方法供页面调用

//    @Transactional(transactionManager = "lexiconTransactionManager", rollbackFor = Exception.class)
    public void loadData(){
        mapper.deleteAll();
        insertToDB(PATH_DIC_MAIN, MAIN_LEXICONS_KEY, INITIALARRAYSIZE_DIC_MAIN);
        insertToDB(PATH_DIC_QUANTIFIER, QUANTIFIER_LEXICONS_KEY, INITIALARRAYSIZE_DIC_QUANTIFIER);
    }

    private void insertToDB(String fileName, String key, int initialArraySize){
        try {
            List<Lexicon> lexicons = getLexiconsFromFS(fileName, initialArraySize, key);
            int size = lexicons.size();
            if(size <= BATCH_SIZE) {
                mapper.batchAddLexicon(lexicons);
            }else {
                List<List<Lexicon>> partitions = Lists.partition(lexicons, BATCH_SIZE);
                partitions.forEach(partition->CompletableFuture.runAsync(()->mapper.batchAddLexicon(partition)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Lexicon> getLexiconsFromFS(String fileName, int initialArraySize, String key) throws IOException {
        @Cleanup
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
        if (is == null) {
            throw new RuntimeException(fileName + "not found!!!");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is,
                "UTF-8"), 512);
        String theWord = null;
        List<Lexicon> lexicons =Lists.newArrayListWithCapacity(initialArraySize);
        do {
            theWord = br.readLine();
            if (StringUtils.isNotEmpty(theWord)) {
                lexicons.add(new Lexicon(UUIDUtil.getUUID(), theWord.trim().toLowerCase(), key));
            }
        } while (theWord != null);
        return lexicons;
    }

    public List<KeyValue> getKvs() {
        return Collections.unmodifiableList(kvs);
    }

    public void addExtLexicon(String lexicon) {
        lexiconCacheManager.addLexicon(EXT_LEXICONS_KEY, lexicon);
    }

    public void addStopLexicon(String lexicon) {
        lexiconCacheManager.addLexicon(STOP_LEXICONS_KEY, lexicon);
    }

    public void deleteExtLexicon(String lexicon) {
        lexiconCacheManager.deleteLexicon(EXT_LEXICONS_KEY, lexicon);
    }

    public void deleteStopLexicon(String lexicon) {
        lexiconCacheManager.deleteLexicon(STOP_LEXICONS_KEY, lexicon);
    }

    public PageInfo<String> findPageLexicons(int pageNum, int pageSize, String key) {
        PageHelper.startPage(pageNum, pageSize);
        List<String> lexicons = mapper.getLexicons(key);
        return new PageInfo(lexicons);
    }

    public PageInfo<String> findPageLexicons(int pageNum, int pageSize, String key, String search) {
        PageHelper.startPage(pageNum, pageSize);
        List<String> lexicons = mapper.getLexiconsForSearch(key, search);
        return new PageInfo(lexicons);
    }

    //一下方法供ikanalyzer使用

    public Set<String> getMainLexicons() {
        return Collections.unmodifiableSet(lexiconCacheManager.getLexiconsFromCache(MAIN_LEXICONS_KEY));
    }

    public Set<String> getQuantifierLexicons() {
        return Collections.unmodifiableSet(lexiconCacheManager.getLexiconsFromCache(QUANTIFIER_LEXICONS_KEY));
    }

    public Set<String> getExtLexicons() {
        return Collections.unmodifiableSet(lexiconCacheManager.getLexiconsFromCache(EXT_LEXICONS_KEY));
    }

    public Set<String> getStopLexicons() {
        return Collections.unmodifiableSet(lexiconCacheManager.getLexiconsFromCache(STOP_LEXICONS_KEY));
    }
}
