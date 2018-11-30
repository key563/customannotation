package com.key.customannotation;

import com.key.customannotation.annotation.CheckAllNotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CustomAnnotation {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        CustomAnnotation m = applicationContext.getBean(CustomAnnotation.class);
        m.test("hello", null);
    }

    @CheckAllNotNull
    public void test(String var1, String var2) {
        System.out.println(var1 + var2);
    }
}
