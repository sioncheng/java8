package com.github.sioncheng.j.exception;

public class DirtyResource implements AutoCloseable {

    public void access() {
        throw new RuntimeException("dirty resource!");
    }

    @Override
    public void close() {
        throw new NullPointerException("npe!");
    }
}
