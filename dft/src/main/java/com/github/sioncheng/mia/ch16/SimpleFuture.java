package com.github.sioncheng.mia.ch16;

import java.util.concurrent.*;

public class SimpleFuture {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                return 1;
            }
        });


        int o = future.get(2, TimeUnit.SECONDS) + 10;
        System.out.println(o);

        executorService.shutdown();
    }
}
