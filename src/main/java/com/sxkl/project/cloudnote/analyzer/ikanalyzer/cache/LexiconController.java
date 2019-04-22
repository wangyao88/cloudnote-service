package com.sxkl.project.cloudnote.analyzer.ikanalyzer.cache;

import com.github.pagehelper.PageInfo;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/lexicon")
public class LexiconController {

    @Autowired
    private LexiconService lexiconService;

    @PostMapping("/addExtLexicon")
    @ResponseBody
    public void addExtLexicon(String lexicon) {
        lexiconService.addExtLexicon(lexicon);
    }

    @PostMapping("/addStopLexicon")
    @ResponseBody
    public void addStopLexicon(String lexicon) {
        lexiconService.addStopLexicon(lexicon);
    }

    @PostMapping("/deleteExtLexicon")
    @ResponseBody
    public void deleteExtLexicon(String lexicon) {
        lexiconService.deleteExtLexicon(lexicon);
    }

    @PostMapping("/deleteStopLexicon")
    @ResponseBody
    public void deleteStopLexicon(String lexicon) {
        lexiconService.deleteStopLexicon(lexicon);
    }

    @GetMapping("/findPageLexicons")
    @ResponseBody
    public PageInfo<String> findPageLexicons(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize,
                                             @RequestParam("key") String key, @RequestParam("search") String search) {
        if(StringUtils.isEmpty(search)) {
            return lexiconService.findPageLexicons(pageNum, pageSize, key);
        }
        return lexiconService.findPageLexicons(pageNum, pageSize, key, "%"+search+"%");
    }

    @PostMapping("loadData")
    @ResponseBody
    public void loadData() {
        lexiconService.loadData();
    }

    @GetMapping("/analysis")
    @ResponseBody
    public List<String> analysis(@RequestParam("words")  String words) {
        return lexiconService.analysis(words);
    }
}
