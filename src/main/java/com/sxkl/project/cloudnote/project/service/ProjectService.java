package com.sxkl.project.cloudnote.project.service;

import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.project.entity.Project;
import com.sxkl.project.cloudnote.project.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService extends BaseService<Project> {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    protected BaseMapper<Project> getMapper() {
        return projectMapper;
    }

    public OperateResult checkName(String name) {
        Project project = new Project();
        project.setName(name);
        List<Project> companies = projectMapper.findByName(project);
        if(companies.isEmpty()) {
            return OperateResult.buildSuccess();
        }
        return OperateResult.buildFail();
    }
}
