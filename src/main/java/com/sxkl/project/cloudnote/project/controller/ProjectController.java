package com.sxkl.project.cloudnote.project.controller;

import com.sxkl.project.cloudnote.base.controller.BaseController;
import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.project.entity.Project;
import com.sxkl.project.cloudnote.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController<Project> {

    @Autowired
    private ProjectService projectService;

    @Override
    protected BaseService<Project> getBaseService() {
        return projectService;
    }

    @PostMapping("checkName")
    @ResponseBody
    public OperateResult checkName(@RequestParam("name") String name) {
        return projectService.checkName(name);
    }
}
