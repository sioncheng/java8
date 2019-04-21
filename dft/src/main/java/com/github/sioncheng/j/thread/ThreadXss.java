package com.github.sioncheng.j.thread;

import java.util.concurrent.TimeUnit;

public class ThreadXss {

    public static void main(String[] args) throws Exception {
        int c = System.in.read();

        while (c != (int)'a' && c != (int)'s' ) {
            c = System.in.read();
        }

        if (c == (int)'a') activeThreads();
        if (c == (int)'s') sleepThreads();

        InheritableThreadLocal<Integer> ita = new InheritableThreadLocal<>();
        ita.set(100);


    }

    private static void activeThreads() {
        for (int i = 0 ; i < 10; i++) {
            new Thread(null, new Runnable() {
                @Override
                public void run() {
                    int n = 0;
                    int a,b,c,d,e,f,g,h = 0;
                    boolean itr = false;
                    while (!itr) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException ite) {
                            itr = true;
                        }
                        n = n * n;
                        System.out.println(n + " " + Thread.currentThread().getId());
                    }
                }
            }, "t" + i, 40960).start();
        }
    }

    private static void sleepThreads() {
        for (int i = 0 ; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean itr = false;
                    while (!itr) {
                        try {
                            TimeUnit.SECONDS.sleep(10);
                        } catch (InterruptedException ite) {
                            itr = true;
                        }
                    }
                }
            }).start();
        }
    }

}
