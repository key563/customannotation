package com.key.customannotation.scanner;

import com.key.customannotation.p1.BizService;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class RefrenceAnnotationFactoryBean<T> implements FactoryBean<T> {

    private Class<T> mapperInterface;
    public RefrenceAnnotationFactoryBean() {
    }

    public RefrenceAnnotationFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public T getObject() throws Exception {
        return (T) InterfaceProxy.newInstance(mapperInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    public void setMapperInterface(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }
}