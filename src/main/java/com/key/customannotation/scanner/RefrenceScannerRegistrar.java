package com.key.customannotation.scanner;

import com.key.customannotation.annotation.RefrenceScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

public class RefrenceScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware{

    public static final Logger LOGGER = LoggerFactory.getLogger(RefrenceScannerRegistrar.class);

    private ResourceLoader resourceLoader;
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(RefrenceScan.class.getName()));

        AnnotationScanner  scanner = new AnnotationScanner(registry);
        if(resourceLoader != null){
            scanner.setResourceLoader(resourceLoader);
        }
        scanner.doScan("com.key.customannotation.p1");
    }
}
