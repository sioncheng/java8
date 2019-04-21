package com.github.sioncheng.j.concurrent;

import java.util.Random;

public class Semaphore {

    public static void main(String[] args) {
        final java.util.concurrent.Semaphore semaphore = new java.util.concurrent.Semaphore(2);
        final Random random = new Random();

        for (int i = 0 ; i < 4; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println("thread " + Thread.currentThread().getName() + " is working");
                        Util.simpleSleep(random.nextInt(2000));
                        System.out.println("thread " + Thread.currentThread().getName() + " is working");
                        semaphore.release();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            t.setName("t=" + i);

            t.start();
        }
    }
}
