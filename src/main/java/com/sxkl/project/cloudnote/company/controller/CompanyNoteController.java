package com.sxkl.project.cloudnote.company.controller;

import com.sxkl.project.cloudnote.base.controller.BaseController;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.company.entity.CompanyNote;
import com.sxkl.project.cloudnote.company.service.CompanyNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/companyNote")
public class CompanyNoteController extends BaseController<CompanyNote> {

    @Autowired
    private CompanyNoteService companyNoteService;

    @Override
    protected BaseService<CompanyNote> getBaseService() {
        return companyNoteService;
    }
}
