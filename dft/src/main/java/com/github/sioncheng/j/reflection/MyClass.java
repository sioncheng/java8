package com.github.sioncheng.j.reflection;

public class MyClass {

    @MyAnnotation(value = {"a", "b", "c"})
    public void execute() {
        System.out.println("MyClass.execute");
    }
}
