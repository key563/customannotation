package com.key.customannotation.p1;

import com.key.customannotation.annotation.Refrence;

@Refrence
public class BizServiceImpl implements BizService {
    @Override
    public String getName(String name) {
        return name;
    }
}
