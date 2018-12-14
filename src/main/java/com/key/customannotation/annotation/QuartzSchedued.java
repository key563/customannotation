package com.key.customannotation.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface QuartzSchedued {
    String triggerName() default "";

    String jobName() default "";

    String groupName() default "";

    String cron() default "";
}
