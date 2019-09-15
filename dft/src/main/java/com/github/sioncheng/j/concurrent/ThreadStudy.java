package com.github.sioncheng.j.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadStudy {
    public static void main(String[] args) {
        final Thread.UncaughtExceptionHandler exceptionHandler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                synchronized (this) {
                    System.out.println("Uncaught exception in thread '" + t.getName() + "': " + e.getMessage());
                }
            }
        };
        // 自定义线程的newThread方法以加入自己的Handler
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                System.out.println("creating pooled thread");
                final Thread thread = new Thread(r);
                //以为这样可以抓住执行过程的异常
                thread.setUncaughtExceptionHandler(exceptionHandler);
                return thread;
            }
        };
        ExecutorService threadPool
                = Executors.newFixedThreadPool(1, threadFactory);
        Callable callable = new Callable() {
            @Override
            public Integer call() throws Exception {
                return  100 / 0;
            }
        };
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                //int j = 100 / 0;
                throw new Error("aaa");
            }
        });
        threadPool.shutdown();
        System.out.println("-----------Done.---------");
    }
}