package com.github.sioncheng.j.concurrent;

public interface GoChan<T> {
    void send(T t);

    T receive();
}
