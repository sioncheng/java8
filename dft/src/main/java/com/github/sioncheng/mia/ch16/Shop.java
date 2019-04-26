package com.github.sioncheng.mia.ch16;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.sioncheng.mia.ch16.Util.delayOneSecond;

public class Shop {

    private String name;

    private final Random random = new Random();

    private static final AtomicInteger id = new AtomicInteger(0);
    private static final ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactory(){
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName(String.format("ShopExecutors-%d", id.getAndIncrement()));
            t.setDaemon(true);

            return t;
        }
    });

    public Shop(){
        this("");
    }

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice(String product) {
        System.out.println(String.format("getPrice in thread %s", Thread.currentThread().getName()));
        delayOneSecond();
        if (System.currentTimeMillis() % 3 == 0) {
            throw new RuntimeException("woooooo!");
        }
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public Future<Double> getPriceAsync(final String product) {
        final CompletableFuture<Double> completableFuture = new CompletableFuture<>();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    completableFuture.complete(getPrice(product));
                } catch (Exception ex) {
                    completableFuture.completeExceptionally(ex);
                }
            }
        });

        return completableFuture;
    }

    public Future<Double> getPriceAsync2(final String product) {
        return CompletableFuture.supplyAsync(()->getPrice(product));
    }



    public static void main(String[] args) throws Exception{
        Shop shop = new Shop();
        Future<Double> future = shop.getPriceAsync2("apple");
        System.out.println("do something else");
        double price = future.get(2, TimeUnit.SECONDS);
        System.out.println(price);
    }
}
