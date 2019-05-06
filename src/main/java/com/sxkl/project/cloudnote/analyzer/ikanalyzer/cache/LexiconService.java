package com.sxkl.project.cloudnote.analyzer.ikanalyzer.cache;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sxkl.project.cloudnote.analyzer.ikanalyzer.dic.Dictionary;
import com.sxkl.project.cloudnote.analyzer.ikanalyzer.lucene.IKAnalyzer;
import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.etl.utils.ObjectUtils;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import com.sxkl.project.cloudnote.etl.utils.UUIDUtil;
import lombok.Cleanup;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class LexiconService {

    private static final String PATH_DIC_MAIN = "ikanalyzer/main2012.dic";
    private static final String PATH_DIC_QUANTIFIER = "ikanalyzer/quantifier.dic";
    private static final int INITIALARRAYSIZE_DIC_MAIN = 275713;
    private static final int INITIALARRAYSIZE_DIC_QUANTIFIER = 316;
    private static final int BATCH_SIZE = 1000;

    private static Map<String, String> kvs = Maps.newHashMap();

    @Autowired
    private LexiconMapper mapper;
    @Autowired
    private LexiconCacheManager lexiconCacheManager;
    @Autowired
    @Qualifier("mainSqlSessionTemplate")
    private SqlSessionTemplate mainSqlSessionTemplate;

    @PostConstruct
    private void init() {
        kvs.put(LexiconConstant.MAIN_LEXICONS_KEY, "主词库");
        kvs.put(LexiconConstant.QUANTIFIER_LEXICONS_KEY, "量词库");
        kvs.put(LexiconConstant.EXT_LEXICONS_KEY, "扩展词库");
        kvs.put(LexiconConstant.STOP_LEXICONS_KEY, "停用词库");
    }

    //以下方法供页面调用

//    @Transactional(transactionManager = "mainTransactionManager", rollbackFor = Exception.class)
    public OperateResult loadData(){
        try {
            mapper.deleteAll();
            insertToDB(PATH_DIC_MAIN, LexiconConstant.MAIN_LEXICONS_KEY, INITIALARRAYSIZE_DIC_MAIN);
            insertToDB(PATH_DIC_QUANTIFIER, LexiconConstant.QUANTIFIER_LEXICONS_KEY, INITIALARRAYSIZE_DIC_QUANTIFIER);
        }catch (Exception e) {
            return OperateResult.buildFail(e);
        }
        return OperateResult.buildSuccess();
    }

    private void insertToDB(String fileName, String key, int initialArraySize) throws Exception {
        List<Lexicon> lexicons = getLexiconsFromFS(fileName, initialArraySize, key);
        int size = lexicons.size();
        if(size <= BATCH_SIZE) {
            mapper.batchAddLexicon(lexicons);
        }else {
            List<List<Lexicon>> partitions = Lists.partition(lexicons, BATCH_SIZE);
            partitions.forEach(partition->CompletableFuture.runAsync(()->mapper.batchAddLexicon(partition)));
        }
    }

    public OperateResult refreshDict() {
        try {
            Dictionary dictionary = Dictionary.getImmediateSingleton();
            if(ObjectUtils.isNull(dictionary)) {
                dictionary = Dictionary.initial(null);
            }
            dictionary.refreshDict();
        }catch (Exception e) {
            return OperateResult.buildFail(e);
        }
        return OperateResult.buildSuccess();
    }

    public List<String> analysis(String words) {
        Analyzer analyzer = new IKAnalyzer(true);
        List<String> result = Lists.newArrayList();
        try {
            @Cleanup
            TokenStream ts = analyzer.tokenStream("myfield", new StringReader(words));
            CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
            ts.reset();
            while (ts.incrementToken()) {
                result.add(term.toString());
            }
            ts.end();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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

    public Map<String, String> getKvs() {
        return Collections.unmodifiableMap(kvs);
    }

    public void addExtLexicon(String lexicon) {
        lexiconCacheManager.addLexicon(LexiconConstant.EXT_LEXICONS_KEY, lexicon);
        Dictionary dictionary = Dictionary.getImmediateSingleton();
        if(ObjectUtils.isNotNull(dictionary)) {
            dictionary.addExtWords(Lists.newArrayList(lexicon));
        }
    }

    public void addStopLexicon(String lexicon) {
        lexiconCacheManager.addLexicon(LexiconConstant.STOP_LEXICONS_KEY, lexicon);
        Dictionary dictionary = Dictionary.getImmediateSingleton();
        if(ObjectUtils.isNotNull(dictionary)) {
            dictionary.addStopWords(Lists.newArrayList(lexicon));
        }
    }

    public void deleteExtLexicon(String lexicon) {
        lexiconCacheManager.deleteLexicon(LexiconConstant.EXT_LEXICONS_KEY, lexicon);
        Dictionary dictionary = Dictionary.getImmediateSingleton();
        if(ObjectUtils.isNotNull(dictionary)) {
            dictionary.disableExtWords(Lists.newArrayList(lexicon));
        }
    }

    public void deleteStopLexicon(String lexicon) {
        lexiconCacheManager.deleteLexicon(LexiconConstant.STOP_LEXICONS_KEY, lexicon);
        Dictionary dictionary = Dictionary.getImmediateSingleton();
        if(ObjectUtils.isNotNull(dictionary)) {
            dictionary.disableStopWords(Lists.newArrayList(lexicon));
        }
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
        return Collections.unmodifiableSet(lexiconCacheManager.getLexiconsFromCache(LexiconConstant.MAIN_LEXICONS_KEY));
    }

    public Set<String> getQuantifierLexicons() {
        return Collections.unmodifiableSet(lexiconCacheManager.getLexiconsFromCache(LexiconConstant.QUANTIFIER_LEXICONS_KEY));
    }

    public Set<String> getExtLexicons() {
        return Collections.unmodifiableSet(lexiconCacheManager.getLexiconsFromCache(LexiconConstant.EXT_LEXICONS_KEY));
    }

    public Set<String> getStopLexicons() {
        return Collections.unmodifiableSet(lexiconCacheManager.getLexiconsFromCache(LexiconConstant.STOP_LEXICONS_KEY));
    }

    public List<Map<String, Object>> statisticLexcion() {
        List<Map<String, Object>> datas = mapper.statisticLexcion();
        datas.forEach(data -> {
            Object discriminator = data.get("discriminator");
            String prettyDiscriminator = kvs.get(data.get("discriminator"));
            data.put("prettyDiscriminator", prettyDiscriminator);
        });
        return datas;
    }
}
