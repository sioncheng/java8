package com.github.sioncheng.j.thread;

public class UncaughtException {

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 10; i >= 0; i--) {
                    System.out.println(21 / i);
                }
            }
        });

        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName());
                e.printStackTrace();
            }
        });

        t.setName("UncaughtException");

        t.start();

        t.join();
    }
}
