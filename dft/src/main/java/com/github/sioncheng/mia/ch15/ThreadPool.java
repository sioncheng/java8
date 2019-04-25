package com.github.sioncheng.mia.ch15;

import java.util.concurrent.*;

public class ThreadPool {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = getThreadPoolExecutor();

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("run in " + Thread.currentThread().getId());
            }
        });

        //Thread.sleep(1000);
        //executorService.shutdown();

        Thread.sleep(100000000);

    }

    static ExecutorService getFixedThreadPool() {
        return Executors.newFixedThreadPool(1);
    }

    static ExecutorService getThreadPoolExecutor() {
        return new ThreadPoolExecutor(0, 1,
                100L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

}
