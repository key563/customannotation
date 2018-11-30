package com.key.customannotation.controller;

import com.key.customannotation.annotation.CheckNotNull;
import com.key.customannotation.annotation.LogAno;
import com.key.customannotation.annotation.NotNull;
import com.key.customannotation.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/syslog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @LogAno("测试")
    @CheckNotNull
    @GetMapping("/test")
    public String test(@NotNull String name) {
        //这样无效，因为没有经过spring容器中获取bean的方式进行调用，springAOP无法捕获
        test2(name, null);
        return name;
    }


    @CheckNotNull
    @GetMapping("/test2")
    public void test2(String name, @NotNull String value) {
        System.out.println(name + ":" + value);
    }
}
