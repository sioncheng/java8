package com.github.sioncheng.j8.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class CompletableFutureMain {

    public static void main(String[] args) {

        //comp1();

        //comp2();

        comp3();

    }

    private static void comp1() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (System.currentTimeMillis() % 2 == 0) {
                    completableFuture.complete("ok");
                } else {
                    completableFuture.completeExceptionally(new Error("oops"));
                }
            }
        }).start();

        try {
            System.out.println(completableFuture.get());
        } catch (InterruptedException ite) {
            ite.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void comp2() {
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                if (System.currentTimeMillis() % 2 == 0) {
                    return "ok 2";
                } else {
                    throw new Error("oops 2");
                }
            }
        });

        try {
            System.out.println(completableFuture2.get());
        } catch (InterruptedException ite) {
            ite.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void comp3() {

        CompletableFuture<String> completableFuture3 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                if (System.currentTimeMillis() % 2 == 0) {
                    return "ok 3";
                } else {
                    throw new Error("oops 3");
                }
            }
        });

        Future<String> future = completableFuture3.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                if (throwable != null) {
                    System.out.println("accept " + throwable.getMessage());
                } else {
                    System.out.println("accpet " + s);
                }
            }
        });

        try {
            System.out.println(future.get());
        } catch (InterruptedException ite) {
            ite.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
