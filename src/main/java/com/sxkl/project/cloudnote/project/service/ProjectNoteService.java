package com.sxkl.project.cloudnote.project.service;

import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.project.entity.ProjectNote;
import com.sxkl.project.cloudnote.project.mapper.ProjectNoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectNoteService extends BaseService<ProjectNote> {

    @Autowired
    private ProjectNoteMapper projectNoteMapper;

    @Override
    protected BaseMapper<ProjectNote> getMapper() {
        return projectNoteMapper;
    }
}
