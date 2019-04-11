package com.github.sioncheng.j.concurrent;

import java.util.concurrent.Exchanger;

public class ExchangerTest {

    public static void main(String... args) {
        System.out.println("exchanger test");

        final Exchanger<Integer> exchanger = new Exchanger<>();

        new Thread(() -> {
            System.out.println("producer...");
            System.out.println("producer...");
            System.out.println("producer...");
            sleep(100);
            try {
                System.out.println("get value in producer " + exchanger.exchange(1));
            } catch (InterruptedException ie) {

            }

        }).start();

        new Thread(() -> {
            System.out.println("consumer...");
            try {
                System.out.println("get value in consumer " + exchanger.exchange(2));
            } catch (InterruptedException ie) {

            }
            System.out.println("consumer...");
            System.out.println("consumer...");


        }).start();

    }

    static boolean sleep(int ms) {
        try {
            Thread.sleep(ms);
            return true;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}
