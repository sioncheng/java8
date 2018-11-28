package com.github.sioncheng.j8.interfaces;

public interface AInterface {

    public default void methodA() {
        System.out.println(getA());
    }

    int getA();
}
