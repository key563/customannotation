package com.key.customannotation.job;

import com.key.customannotation.annotation.QuartzSchedued;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.annotation.Scheduled;

@QuartzSchedued(cron = "*/10 * * * * ?")
public class CustomJob implements Job {
    @Override
    @Scheduled(cron = "a")
    public void execute(JobExecutionContext jobExecutionContext)  {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        System.out.println("hello ");
    }
}
