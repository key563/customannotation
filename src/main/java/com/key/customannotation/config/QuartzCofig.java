package com.key.customannotation.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzCofig {

    @Bean(value = "scheduler")
    public Scheduler getScheduler() throws SchedulerException {
        return new StdSchedulerFactory().getScheduler();
    }
}
