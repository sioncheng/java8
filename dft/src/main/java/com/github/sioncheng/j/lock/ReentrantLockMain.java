package com.github.sioncheng.j.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockMain {

    public static void main(String[] args) throws InterruptedException{
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        //reentrantLock.lockInterruptibly();

    }
}
