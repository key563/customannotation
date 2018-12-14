package com.key.customannotation.scanner;

import com.key.customannotation.entity.Syslog;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Objects;

public class InterfaceProxy implements InvocationHandler {
    private Object obj;

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("ObjectProxy execute:" + method.getName());
        Syslog logAno = new Syslog();
        logAno.setExeuTime(System.nanoTime());
        logAno.setClassName(method.getDeclaringClass().getName());
        logAno.setMethodName(method.getName());
        logAno.setParams(Arrays.toString(args));
        System.out.println(logAno.toString());
        proxy.getClass();
        Annotation[] annotations = method.getDeclaringClass().getDeclaredAnnotations();
        return method.invoke(this, args);
    }

    public static <T> T newInstance(Class<T> innerInterface) throws IllegalAccessException, InstantiationException {
        ClassLoader classLoader = innerInterface.getClassLoader();
        Class[] interfaces = new Class[]{innerInterface};
        InterfaceProxy proxy = new InterfaceProxy();
        return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
    }

}
