package com.github.sioncheng.j.spi;

public class ServiceAImpl implements ServiceA {

    public String methodA(String a) {
        return "ServiceImpl#" + a;
    }
}
