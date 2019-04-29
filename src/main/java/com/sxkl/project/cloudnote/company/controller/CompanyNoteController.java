package com.sxkl.project.cloudnote.company.controller;

import com.sxkl.project.cloudnote.base.controller.BaseController;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.company.entity.CompanyNote;
import com.sxkl.project.cloudnote.company.service.CompanyNoteService;
import com.sxkl.project.cloudnote.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/companyNote")
public class CompanyNoteController extends BaseController<CompanyNote> {

    @Autowired
    private CompanyNoteService companyNoteService;
    @Autowired
    private CompanyService companyService;

    @Override
    protected BaseService<CompanyNote> getBaseService() {
        return companyNoteService;
    }

    @Override
    public ModelAndView tablePage() {
        ModelAndView mv = new ModelAndView(getViewName("table"));
        mv.addObject("companies", companyService.findAll());
        return mv;
    }
}
