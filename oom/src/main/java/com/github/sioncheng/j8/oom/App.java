package com.github.sioncheng.j8.oom;


import java.util.concurrent.ConcurrentLinkedQueue;

public class App {

    private static ConcurrentLinkedQueue<String> linkedQueue =
            new ConcurrentLinkedQueue<>();



    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;;i++) {

                    for (int j=0; i < 1000;j++) {
                        long m = i * j + 1;
                        linkedQueue.add("simple text " + m);
                    }


                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }
        });


        t.start();


        t.join();
    }
}
