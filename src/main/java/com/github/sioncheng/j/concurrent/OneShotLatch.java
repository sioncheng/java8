package com.github.sioncheng.j.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class OneShotLatch {

    private final Sync sync = new Sync();

    public void signal() {
        sync.release(0);
    }

    public void await() throws InterruptedException {
        sync.acquireInterruptibly(0);
    }


    public static void main(String[] args) throws Exception {

        final OneShotLatch oneShotLatch = new OneShotLatch();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    oneShotLatch.await();
                } catch (InterruptedException ie) {
                    return;
                }

                System.out.println("awaited");
            }
        });

        t.start();
        System.out.println("started");

        Thread.sleep(100);

        oneShotLatch.signal();
    }

    private class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            return compareAndSetState(1, 0);
        }

        @Override
        protected boolean tryRelease(int arg) {
            return compareAndSetState(0, 1);
        }
    }
}
