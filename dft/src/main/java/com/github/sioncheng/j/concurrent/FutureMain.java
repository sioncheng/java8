package com.github.sioncheng.j.concurrent;

import java.util.concurrent.*;

public class FutureMain {

    public static void main(String[] args) throws Exception {


        ThreadPoolExecutor service = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());

        Future<Integer> f = service.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                Thread.sleep(1000);
                return (int) (System.currentTimeMillis() / 1000);
            }
        });

        System.out.println(f.get().intValue());
        service.shutdown();
    }
}
