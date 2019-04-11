package com.github.sioncheng.j.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class GoChan<T> {
    private T t;
    private ReentrantLock lock = new ReentrantLock();
    private Condition hasReceiver = lock.newCondition();
    private Condition hasSender = lock.newCondition();

    public GoChan() {

    }

    public void send(T t) {
        lock.lock();
        try {
            this.t = t;
            hasSender.signalAll();
            hasReceiver.await();
        } catch (InterruptedException ie) {

        } finally {
            lock.unlock();
        }
    }

    public T receive() {
        T result = null;
        lock.lock();
        try {
            hasReceiver.signalAll();
            hasSender.await();
            result = this.t;
        } catch (InterruptedException ie) {

        } finally {
            lock.unlock();
        }
        return result;
    }
}
