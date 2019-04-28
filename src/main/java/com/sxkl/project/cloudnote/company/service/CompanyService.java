package com.sxkl.project.cloudnote.company.service;

import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.company.entity.Company;
import com.sxkl.project.cloudnote.company.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService extends BaseService<Company> {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    protected BaseMapper<Company> getMapper() {
        return companyMapper;
    }

    public OperateResult checkName(String name) {
        Company company = new Company();
        company.setName(name);
        List<Company> companies = companyMapper.findByName(company);
        if(companies.isEmpty()) {
            return OperateResult.buildSuccess();
        }
        return OperateResult.buildFail();
    }
}
