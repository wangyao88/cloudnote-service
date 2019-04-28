package com.sxkl.project.cloudnote.company.controller;

import com.sxkl.project.cloudnote.base.controller.BaseController;
import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.company.entity.Company;
import com.sxkl.project.cloudnote.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController<Company> {

    @Autowired
    private CompanyService companyService;

    @Override
    protected BaseService<Company> getBaseService() {
        return companyService;
    }

    @PostMapping("checkName")
    @ResponseBody
    public OperateResult checkName(@RequestParam("name") String name) {
        return companyService.checkName(name);
    }
}
