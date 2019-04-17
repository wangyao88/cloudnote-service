package com.sxkl.project.cloudnote.etl.quartzjob;

import com.sxkl.project.cloudnote.etl.service.EtlManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.Date;

public class EtlJob extends QuartzJobBean {

    @Resource
    private EtlManager etlManager;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        etlManager.doEtlGroup();
    }
}
