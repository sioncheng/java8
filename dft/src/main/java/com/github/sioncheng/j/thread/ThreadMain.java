package com.github.sioncheng.j.thread;

public class ThreadMain {

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new Runnable() {
            public void run() {
                System.out.println("run");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        });

        t.start();
        //t.start(); this will cause thread state exception

        t.join();
    }
}
