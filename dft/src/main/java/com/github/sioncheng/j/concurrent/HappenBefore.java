package com.github.sioncheng.j.concurrent;

import sun.misc.Unsafe;
import sun.reflect.Reflection;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class HappenBefore {

    static class Actor implements Runnable {

        int v = 0;

        public int getV() {
            return v;
        }

        @Override
        public void run() {
            v+=1;
        }
    }

    static class ActorSafeWrapper implements Runnable {

        AtomicInteger status = new AtomicInteger(0);
        Actor actor ;
        public ActorSafeWrapper(Actor actor) {
            this.actor = actor;
        }

        @Override
        public void run() {
            while (!status.compareAndSet(0, 1)) {
                System.out.print(".");
            }
            this.actor.run();
            while (!status.compareAndSet(1, 0)) {
                System.out.print("-");
            }
        }

        public int getV() {
            status.get();
            int v = this.actor.getV();
            status.get();
            return v;
        }
    }

    public static void main(String[] args) throws Exception{

        Actor actor = new Actor();

        ActorSafeWrapper actorSafeWrapper = new ActorSafeWrapper(actor);

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        for (int i = 0 ;i < 2000;i++) {
            forkJoinPool.submit(actorSafeWrapper);
        }

        Thread.sleep(3000);

        System.out.println(actorSafeWrapper.getV());

        forkJoinPool.shutdown();
    }
}
