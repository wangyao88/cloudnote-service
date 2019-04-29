package com.sxkl.project.cloudnote.company.service;

import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.company.entity.CompanyNote;
import com.sxkl.project.cloudnote.company.mapper.CompanyNoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyNoteService extends BaseService<CompanyNote> {

    @Autowired
    private CompanyNoteMapper companyNoteMapper;

    @Override
    protected BaseMapper<CompanyNote> getMapper() {
        return companyNoteMapper;
    }
}
