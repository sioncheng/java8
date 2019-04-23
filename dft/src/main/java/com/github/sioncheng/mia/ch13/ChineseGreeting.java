package com.github.sioncheng.mia.ch13;

class ChineseGreeting implements Greeting{
    @Override
    public void hi(String s) {
        System.out.println("你好 " + s);
    }
}