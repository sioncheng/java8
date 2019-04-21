package com.github.sioncheng.j.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoin {

    public static void main(String... args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        System.out.println(forkJoinPool.getParallelism());
        System.out.println(Runtime.getRuntime().availableProcessors());

        System.out.println(forkJoinPool.invoke(new FibonacciComputation(100)));
        forkJoinPool.shutdown();
    }


    public static class FibonacciComputation extends RecursiveTask<Long> {
        private static ConcurrentHashMap<Integer, Long> computedCache = new ConcurrentHashMap<>();

        private Integer number;

        static {
            computedCache.put(0, Long.valueOf(0));
            computedCache.put(1, Long.valueOf(1));
        }

        FibonacciComputation(Integer number) {
            this.number = number;
        }

        @Override
        protected Long compute() {

            System.out.println(Thread.currentThread().getName());

            Long result = computedCache.get(number);
            if (result != null) {
                System.out.println(Thread.currentThread().getName() + " return from cache");
                return result;
            }



            FibonacciComputation left = new FibonacciComputation(number - 1);
            FibonacciComputation right = new FibonacciComputation(number - 2);

            left.fork();

            result =  right.compute() + left.join();

            computedCache.putIfAbsent(number, result);

            return result;
        }
    }
}
