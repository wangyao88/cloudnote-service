package com.sxkl.project.cloudnote.etl.api;


import com.sxkl.project.cloudnote.etl.service.EtlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/etl")
public class EtlApi {

    @Autowired
    private EtlManager etlManager;

    @GetMapping("/all")
    public String etl() {
        return etlManager.doEtlGroup();
    }
}
