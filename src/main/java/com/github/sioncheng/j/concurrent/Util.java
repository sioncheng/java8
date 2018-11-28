package com.github.sioncheng.j.concurrent;

class Util {

    public static void simpleSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}