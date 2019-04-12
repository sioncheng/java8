package com.github.sioncheng.j.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class GoChanOne<T> implements GoChan<T> {
    private GoChanBuffered<T> goChanBuffered;

    public GoChanOne() {
        goChanBuffered =new GoChanBuffered<>(1);
    }

    public void send(T t) {
        goChanBuffered.send(t);
    }

    public T receive() {
        return goChanBuffered.receive();
    }
}
