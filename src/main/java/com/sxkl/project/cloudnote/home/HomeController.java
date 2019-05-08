package com.sxkl.project.cloudnote.home;


import com.sxkl.project.cloudnote.analyzer.ikanalyzer.cache.LexiconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private LexiconService lexiconService;


    @GetMapping("/")
    public String home1() {
        return "home";
    }

    @GetMapping("home")
    public String home2() {
        return "home";
    }
}
