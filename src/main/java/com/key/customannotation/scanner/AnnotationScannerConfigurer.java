package com.key.customannotation.scanner;

import com.key.customannotation.p1.BizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

import static org.springframework.util.Assert.notNull;

@Component
public class AnnotationScannerConfigurer implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor, InitializingBean, BeanNameAware {

    private ApplicationContext applicationContext;
    private String beanName;
    private Class<?> markerInterface;
    private String basePackage;
    public static Map<String, BizService> bizServiceMap;

    private Logger LOGGER = LoggerFactory.getLogger(AnnotationScannerConfigurer.class);


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        Map<String, BizService> map = applicationContext.getBeansOfType(BizService.class);

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        LOGGER.info("postProcessBeanFactory() beanDefinition的个数=====>" + beanFactory.getBeanDefinitionCount());
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        LOGGER.info("postProcessBeanDefinitionRegistry() beanDefinitionName=====>" + registry.getBeanDefinitionNames().toString());
        // 需要被代理的接口
        AnnotationScanner annotationScanner = new AnnotationScanner(registry);
        annotationScanner.setResourceLoader(applicationContext);
        // "com.pepsi.annotationproxy.service"接口所在的包
//        annotationScanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
        annotationScanner.scan("com.key.customannotation.p1");

    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public String getBeanName() {
        return beanName;
    }

    public Class<?> getMarkerInterface() {
        return markerInterface;
    }

    public void setMarkerInterface(Class<?> markerInterface) {
        this.markerInterface = markerInterface;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}