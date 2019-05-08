package com.sxkl.project.cloudnote.home;


import com.sxkl.project.cloudnote.analyzer.ikanalyzer.cache.LexiconService;
import com.sxkl.project.cloudnote.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private LexiconService lexiconService;
    @Autowired
    private TodoService todoService;


    @GetMapping("/")
    public ModelAndView home1() {
        return getModelAndView();
    }

    @GetMapping("home")
    public ModelAndView home2() {
        return getModelAndView();
    }

    private ModelAndView getModelAndView() {
        ModelAndView mv = new ModelAndView("home");
        List<Map<String, Object>> statisticLexcion = lexiconService.statisticLexcion();
        statisticLexcion.forEach(map->{
            mv.addObject(map.get("discriminator").toString(), map.get("num"));
        });
        mv.addObject("statusMap", todoService.findAllStatusMap());
        return mv;
    }
}
