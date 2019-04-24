package com.sxkl.project.cloudnote.analyzer.ikanalyzer.cache;

import com.github.pagehelper.PageInfo;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/lexicon")
public class LexiconController {

    @Autowired
    private LexiconService lexiconService;

    @PostMapping("/addExtLexicon")
    @ResponseBody
    public void addExtLexicon(@RequestParam("lexicon") String lexicon) {
        lexiconService.addExtLexicon(lexicon);
    }

    @PostMapping("/addStopLexicon")
    @ResponseBody
    public void addStopLexicon(@RequestParam("lexicon") String lexicon) {
        lexiconService.addStopLexicon(lexicon);
    }

    @PostMapping("/deleteExtLexicon")
    @ResponseBody
    public void deleteExtLexicon(@RequestParam("lexicon") String lexicon) {
        lexiconService.deleteExtLexicon(lexicon);
    }

    @PostMapping("/deleteStopLexicon")
    @ResponseBody
    public void deleteStopLexicon(@RequestParam("lexicon") String lexicon) {
        lexiconService.deleteStopLexicon(lexicon);
    }

    @GetMapping("/findPageLexicons")
    @ResponseBody
    public LexiconPageInfo findPageLexicons(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize,
                                            @RequestParam("key") String key, @RequestParam("search") String search) {
        PageInfo<String> pageInfo;
        if(StringUtils.isEmpty(search)) {
            pageInfo = lexiconService.findPageLexicons(pageNum, pageSize, key);
        }else {
            pageInfo = lexiconService.findPageLexicons(pageNum, pageSize, key, "%"+search+"%");
        }
        return new LexiconPageInfo(pageInfo, lexiconService.getKvs().get(key));
    }

    @PostMapping("loadData")
    @ResponseBody
    public void loadData() {
        lexiconService.loadData();
    }

    @PostMapping("refreshDict")
    @ResponseBody
    public void refreshDict() {
        lexiconService.refreshDict();
    }

    @GetMapping("/analysis")
    @ResponseBody
    public List<String> analysis(@RequestParam("words")  String words) {
        return lexiconService.analysis(words);
    }

    @GetMapping("/mainDictPage")
    public String mainDictPage() {
        return "lexicon/dict/main";
    }

    @GetMapping("/quantifierDictPage")
    public String quantifierDictPage() {
        return "lexicon/dict/quantifier";
    }

    @GetMapping("/extDictPage")
    public String extDictPage() {
        return "lexicon/dict/ext";
    }

    @GetMapping("/stopDictPage")
    public String stopDictPage() {
        return "lexicon/dict/stop";
    }

    @GetMapping("/statisticPage")
    public String statisticPage() {
        return "lexicon/statistic";
    }

    @GetMapping("/statisticLexcion")
    @ResponseBody
    public List<Map<String, Object>> statisticLexcion() {
        return lexiconService.statisticLexcion();
    }

    @GetMapping("/analysisPage")
    public String analysisPage() {
        return "lexicon/analysis";
    }
}
