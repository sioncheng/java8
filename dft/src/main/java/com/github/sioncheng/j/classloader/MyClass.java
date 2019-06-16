package com.github.sioncheng.j.classloader;

public class MyClass {

    private MyClassOther myClassOther;

    public MyClass() {
        myClassOther = new MyClassOther();
    }

    @Override
    public String toString() {
        return "MyClass";
    }
}
