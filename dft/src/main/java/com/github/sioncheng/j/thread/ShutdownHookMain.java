package com.github.sioncheng.j.thread;

public class ShutdownHookMain {

    public static void main(String[] args) throws Exception{
        System.out.println("start");

        Runtime.getRuntime().addShutdownHook(new ShutdownHook());


        System.in.read();
    }


    static class ShutdownHook extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("enter shutdown hook, will take 10 minutes to exit.");

                Thread.sleep(10 * 60 * 1000);

                System.out.println("after 10 minutes");
            } catch (InterruptedException ite) {
                ite.printStackTrace();
            }
        }
    }
}
