package com.github.sioncheng.j.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuteServiceShutdown {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(()->{
            try {
                Thread.sleep(10000);
                System.out.println("end");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        executorService.shutdown();
    }
}
