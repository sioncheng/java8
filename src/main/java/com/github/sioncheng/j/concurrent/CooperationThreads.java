package com.github.sioncheng.j.concurrent;

public class CooperationThreads {

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (obj1) {
                    obj1.notifyAll();
                }

                for (int i = 0 ; i < 5; i++) {
                    synchronized (obj2) {
                        try {
                            obj2.wait();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        System.out.println(2 * i + 1);
                    }

                    synchronized (obj1) {
                        obj1.notifyAll();
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (obj2) {
                    obj2.notifyAll();
                }

                for (int i = 0 ; i < 5; i++) {
                    synchronized (obj1) {
                        try {
                            obj1.wait();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        System.out.println(2 * i + 2);
                    }

                    synchronized (obj2) {
                        obj2.notifyAll();
                    }
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    private static Object obj1 = new Object();
    private static Object obj2 = new Object();
}
