package com.sxkl.project.cloudnote.etl.quartzjob;


import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EtlQuartzConfig {

    @Bean(name = "etlJobDetail")
    public JobDetail etlJobDetail(){
        return JobBuilder.newJob(EtlJob.class)
                         .withIdentity("etlJob")
                         .storeDurably()
                         .build();
    }

    @Bean(name = "etlQuartzTrigger")
    public Trigger etlQuartzTrigger(){
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 */2 * * ?");
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .forJob(etlJobDetail())
                .withIdentity("etlJob")
                .withSchedule(scheduleBuilder)
                .build();
        return trigger;
    }
}
