package com.github.sioncheng.j8.concurrent;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {

    public static void main(String... args) {


        StampedLock stampedLock = new StampedLock();
        long stamp = stampedLock.tryOptimisticRead();
        boolean b = stampedLock.validate(stamp);

        System.out.println(b);

        long writeLock = stampedLock.writeLock();
        stampedLock.unlock(writeLock);

        b = stampedLock.validate(stamp);

        System.out.println(b);

        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readWriteLock.readLock();
    }
}
