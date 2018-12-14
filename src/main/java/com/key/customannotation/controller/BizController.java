package com.key.customannotation.controller;

import com.key.customannotation.annotation.QuartzSchdularing;
import com.key.customannotation.p1.BizService;
import com.key.customannotation.p1.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/biz")
@QuartzSchdularing
public class BizController {
    @Autowired
    private BizService bizService;

    @Autowired
    private TestService testService;

    @PostMapping(value = "/getName")
    public void getName(String name) {
        name = bizService.getName(name);
        System.out.println(name);
    }

    @PostMapping(value = "/getNum")
    public void getNum(Integer num) {
        num = testService.getNum(num);
        System.out.println(num);
    }


}
