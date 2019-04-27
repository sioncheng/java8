package com.github.sioncheng.j.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Latch {

    public static void main(String[] args) throws Exception {

        final CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Util.simpleSleep(1000);
                System.out.println("1000");
                countDownLatch.countDown();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Util.simpleSleep(2000);
                System.out.println("2000");
                countDownLatch.countDown();
            }
        }).start();


        countDownLatch.await();

        System.out.println("end");

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Util.simpleSleep(1000);
                    System.out.println("1000");
                    cyclicBarrier.await();
                    System.out.println("harry");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Util.simpleSleep(2000);
                    System.out.println("2000");
                    cyclicBarrier.await();
                    System.out.println("harry");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}
