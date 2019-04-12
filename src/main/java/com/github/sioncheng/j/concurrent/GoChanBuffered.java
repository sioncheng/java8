package com.github.sioncheng.j.concurrent;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class GoChanBuffered<T> implements GoChan<T> {
    private Queue<Object> data;
    private int size;
    private int index;

    private ReentrantLock lock;
    private Condition hasData;
    private Condition hasRoom;

    public GoChanBuffered(int size) {
        this.data = new LinkedList();
        this.size = size;
        this.index = 0;

        this.lock = new ReentrantLock();
        this.hasData = lock.newCondition();
        this.hasRoom = lock.newCondition();
    }

    @Override
    public void send(T t) {
        lock.lock();

        try {
            while (index >= size) {
                hasRoom.await();
            }

            data.add(t);
            index+=1;
            hasData.signalAll();
        } catch (Exception ex) {

        } finally {
            lock.unlock();
        }
    }

    @Override
    public T receive() {
        lock.lock();
        T result = null;
        try {
            while (index == 0) {
                hasData.await();
            }

            result = (T)data.poll();
            index-=1;
            hasRoom.signalAll();
        } catch (Exception ex) {

        } finally {
            lock.unlock();
        }

        return result;
    }
}
