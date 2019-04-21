package com.github.sioncheng.j.concurrent;

import java.util.concurrent.*;

public class ScheduledExecutorServiceExam {

    public static void main(String[] args) {
        final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
                new ScheduledThreadPoolExecutor(2);

        final ScheduledFuture<?> scheduledFuture = scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            int i = 0;
            @Override
            public void run() {
                System.out.println("bee " + (++i));
            }
        },1,1, TimeUnit.SECONDS);

        scheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                scheduledFuture.cancel(true);

                scheduledThreadPoolExecutor.shutdown();
            }
        }, 10, TimeUnit.SECONDS);
    }
}
