package com.key.customannotation.p1;

import com.key.customannotation.annotation.Refrence;

@Refrence
public class TestServiceImpl implements TestService {
    @Override
    public Integer getNum(Integer num) {
        return num;
    }
}
