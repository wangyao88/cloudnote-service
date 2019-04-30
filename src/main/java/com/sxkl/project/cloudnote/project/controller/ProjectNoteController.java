package com.sxkl.project.cloudnote.project.controller;

import com.sxkl.project.cloudnote.base.controller.BaseController;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.project.entity.ProjectNote;
import com.sxkl.project.cloudnote.project.service.ProjectNoteService;
import com.sxkl.project.cloudnote.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/projectNote")
public class ProjectNoteController extends BaseController<ProjectNote> {

    @Autowired
    private ProjectNoteService projectNoteService;
    @Autowired
    private ProjectService projectService;

    @Override
    protected BaseService<ProjectNote> getBaseService() {
        return projectNoteService;
    }

    @Override
    public ModelAndView tablePage() {
        ModelAndView mv = new ModelAndView(getViewName("table"));
        mv.addObject("projects", projectService.findAll());
        return mv;
    }
}
