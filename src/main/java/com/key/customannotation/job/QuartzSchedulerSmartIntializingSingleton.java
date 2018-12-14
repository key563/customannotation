package com.key.customannotation.job;

import com.key.customannotation.annotation.QuartzSchedued;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Map;


@Configuration
public class QuartzSchedulerSmartIntializingSingleton implements SmartInitializingSingleton, ApplicationContextAware {

    private Logger LOGGER = LoggerFactory.getLogger(QuartzSchedulerSmartIntializingSingleton.class);
    private ApplicationContext applicationContext;
    @Autowired
    private Scheduler scheduler;

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Job> map = applicationContext.getBeansOfType(Job.class);
        map.forEach((key, job) -> {
            Annotation[] annotations = job.getClass().getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof QuartzSchedued) {
                    QuartzSchedued quartzSchedued = (QuartzSchedued) annotation;
                    String triggerName = quartzSchedued.triggerName();
                    String jobName = quartzSchedued.jobName();
                    String groupName = quartzSchedued.groupName();
                    String cron = quartzSchedued.cron();

                    if (StringUtils.isEmpty(triggerName)) {
                        LOGGER.error("in class {}: trigger name is empty !", key);
                        triggerName = "trigger_" + key;
                        LOGGER.info("auto generate with tigger name: {}", triggerName);
                    }
                    if (StringUtils.isEmpty(jobName)) {
                        LOGGER.error("in class {}: job name is empty !", key);
                        jobName = "job_" + key;
                        LOGGER.info("auto generate with job name: {}", jobName);
                    }

                    if (StringUtils.isEmpty(groupName)) {
                        LOGGER.error("in class {}: group name is empty !", key);
                        groupName = "group_" + key;
                        LOGGER.info("auto generate with group name: {}", groupName);
                    }
                    buildQuartzTask(triggerName, jobName, groupName, cron, job.getClass(), this.scheduler);
                }
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void buildQuartzTask(String triggerName, String jobName, String groupName, String cron, Class<? extends Job> clazz, Scheduler scheduler) {
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, groupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobName, groupName).build();
        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

}
