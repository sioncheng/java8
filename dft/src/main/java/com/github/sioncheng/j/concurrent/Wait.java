package com.github.sioncheng.j.concurrent;

import java.util.concurrent.TimeUnit;

public class Wait {

    public static void main(String[] args) {
        Object lock = new Object();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }

                synchronized (lock) {
                    lock.notifyAll();
                }
            }
        });

        t.start();

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        System.out.println("end");
    }
}
