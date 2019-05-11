package com.github.sioncheng.j8.async;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CompletableFutureAgain {

    public static void main(String[] args) throws Exception{

        //CompletableFuture<Integer> completableFuture = new CompletableFuture<>();

        //completableFuture.get();

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return null;
            }
        });

        System.out.println(completableFuture.get());

        CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println(String.format("%s", Thread.currentThread().getName()));
                return 1;
            }
        }).thenApply(x -> x * 100)
                .thenAccept(v -> {
                    System.out.println(String.format("%d %s", v, Thread.currentThread().getName()));
                });


    }
}
