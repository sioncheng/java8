package com.github.sioncheng.mia.ch13;

interface Greeting {
    default void hi(String s) {
        System.out.println("hi " + s);
    }
}