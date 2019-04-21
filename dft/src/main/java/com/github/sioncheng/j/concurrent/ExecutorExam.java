package com.github.sioncheng.j.concurrent;

import java.util.concurrent.*;

public class ExecutorExam {

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new Task("task1"));
        service.execute(new Task("task2"));
        service.execute(new Task("task3"));
        Future<Object> f = service.submit(new Callable<Object>() {
            @Override
            public Object call() {
                System.out.println("call 1 @ " + Thread.currentThread().getName());
                return 1;
            }
        });
        System.out.println(f.get());

        service.shutdown();
    }


    static class Task implements Runnable {
        private final String name;

        Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(name + "-[" + i + "] @ " + Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
