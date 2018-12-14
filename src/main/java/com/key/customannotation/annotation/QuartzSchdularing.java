package com.key.customannotation.annotation;

import com.key.customannotation.job.QuartzSchedulerSmartIntializingSingleton;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(QuartzSchedulerSmartIntializingSingleton.class)
public @interface QuartzSchdularing {
}
