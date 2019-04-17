package com.sxkl.project.cloudnote.etl.quartzjob;


import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteBadDataQuartzConfig {

    @Bean(name = "deleteBadDataJobDetail")
    public JobDetail deleteBadDataJobDetail(){
        return JobBuilder.newJob(DeleteBadDataJob.class)
                         .withIdentity("deleteBadDataJob")
                         .storeDurably()
                         .build();
    }

    @Bean(name = "deleteBadDataQuartzTrigger")
    public Trigger deleteBadDataQuartzTrigger(){
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 1 * * ?");
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .forJob(deleteBadDataJobDetail())
                .withIdentity("deleteBadDataJob")
                .withSchedule(scheduleBuilder)
                .build();
        return trigger;
    }
}
