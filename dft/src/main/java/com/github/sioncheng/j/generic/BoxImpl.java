package com.github.sioncheng.j.generic;

public class BoxImpl<E> implements Box<E> {
    private E e;
    @Override
    public void set(E e) {
        this.e = e;
    }

    @Override
    public E get() {
        return e;
    }
}
