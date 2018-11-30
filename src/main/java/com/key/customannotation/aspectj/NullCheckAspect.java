package com.key.customannotation.aspectj;

import com.key.customannotation.annotation.NotNull;
import com.key.customannotation.exception.ParameterIsNullException;
import javafx.util.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class NullCheckAspect {
    // 在这里定义切点，切点为我们之前设置的注解@CheckAllNotNull
    @Pointcut("@annotation(com.key.customannotation.annotation.CheckAllNotNull)")
    public void checkAllNotNullPointcut() {
    }

    // 在这里定义切点，切点为我们之前设置的注解@CheckNotNull
    @Pointcut("@annotation(com.key.customannotation.annotation.CheckNotNull)")
    public void checkNotNullPointcut() {
    }

    // 在这里定义@CheckAllNotNull的增强方法
    @Around("checkAllNotNullPointcut()")
    public Object methodsAnnotatedWithCheckAllNotNull(ProceedingJoinPoint joinPoint) throws Throwable {
        // 首先获取方法的签名，joinPoint中有相应的签名信息
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        // 通过方法的签名可以获取方法本身
        Method method = signature.getMethod();
        // 通过joinPoint获取方法的实际参数的值
        Object[] args = joinPoint.getArgs();
        // 对参数的值进行遍历判断，如果为null则抛出异常
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if(arg == null)
                throw new ParameterIsNullException(method, i + 1);
        }
        // 最后如果校验通过则调用proceed方法进行方法的实际执行
        return joinPoint.proceed();
    }

    // 在这里定义@CheckNotNull的增强方法
    @Around("checkNotNullPointcut()")
    public Object methodsAnnotatedWithCheckNotNull(ProceedingJoinPoint joinPoint) throws Throwable {
        // 首先获取方法的签名，joinPoint中有相应的签名信息
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        // 通过方法的签名可以获取方法本身
        Method method = signature.getMethod();
        // 获取方法的参数列表，注意此方法需JDK8+
        Parameter[] parameters = method.getParameters();
        // 获取参数列表的实际的值
        Object[] args = joinPoint.getArgs();
        // 建立参数列表和实际的值的对应关系
        Pair<Parameter, Object>[] parameterObjectPairs = new Pair[parameters.length];
        for(int i = 0; i < parameters.length; i++) {
            parameterObjectPairs[i] = new Pair<>(parameters[i], args[i]);
        }
        // 对参数进行遍历
        for(int i = 0; i < parameterObjectPairs.length; i++) {
            Pair<Parameter, Object> pair = parameterObjectPairs[i];
            Parameter parameter = pair.getKey();
            Object arg = pair.getValue();
            // 获取参数的注解列表
            NotNull[] notNulls = parameter.getAnnotationsByType(NotNull.class);
            // 如果发现没有@NotNull注解则校验下一个参数
            if(notNulls.length == 0) {
                continue;
            }
            // 如果发现参数有@NotNull注解并且实际值为null则抛出异常
            if(arg == null) {
                throw new ParameterIsNullException(method, i + 1);
            }
        }
        // 校验通过后继续往下执行实际的方法
        return joinPoint.proceed();
    }
}
