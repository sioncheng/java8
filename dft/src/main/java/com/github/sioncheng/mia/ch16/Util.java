package com.github.sioncheng.mia.ch16;

public class Util {

    public static void delayOneSecond() {
        delay(1000);
    }

    public static void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ite) {
            throw new RuntimeException(ite);
        }

    }
}
