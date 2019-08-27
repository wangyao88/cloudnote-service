package com.sxkl.project.cloudnote.search.controller;

import com.sxkl.project.cloudnote.search.entity.HotLabelPageInfo;
import com.sxkl.project.cloudnote.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/hotLabelPage")
    public String hotLabelPage() {
        return "search/hotlabel";
    }

    @GetMapping("/findPageHotLabels")
    @ResponseBody
    public HotLabelPageInfo findPageHotLabels(@RequestParam("pageNum") int pageNum,
                                              @RequestParam("pageSize") int pageSize,
                                              @RequestParam("search") String search) {
        return searchService.findPageHotLabels(pageNum, pageSize, search);
    }

    @PostMapping("/removeLabel")
    @ResponseBody
    public void removeLabel(@RequestParam("label") String label) {
        searchService.removeLabel(label);
    }
}