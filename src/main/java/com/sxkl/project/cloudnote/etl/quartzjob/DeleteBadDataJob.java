package com.sxkl.project.cloudnote.etl.quartzjob;

import com.sxkl.project.cloudnote.etl.mapper.remote.RemoteImageMapper;
import com.sxkl.project.cloudnote.etl.service.EtlManager;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.Date;


@Slf4j
public class DeleteBadDataJob extends QuartzJobBean {

    @Resource
    private RemoteImageMapper remoteImageMapper;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
//            remoteImageMapper.deleteUnusedImage();
            log.info("定时删除无用图片成功！");
        }catch(Exception e) {
            log.error("定时删除无用图片失败", e);
        }
    }
}
