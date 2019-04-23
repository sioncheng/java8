package com.github.sioncheng.mia.ch13;

public class DefaultMethod {

    public static void main(String[] args) {
        Greeting greeting = new EnglishGreeting();
        greeting.hi("sion");

        greeting = new ChineseGreeting();
        greeting.hi("sion");
    }
}
