package com.github.sioncheng.j.concurrent;

import java.util.concurrent.TimeUnit;

public class Join {

    public static void main(String[] args) throws Exception{
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                Thread.yield();

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        });

        t.start();

        t.join();

        System.out.println("end");
    }
}
