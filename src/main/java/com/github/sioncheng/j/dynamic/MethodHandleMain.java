package com.github.sioncheng.j.dynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleMain {

    public static void main(String[] args) throws Throwable{
        System.out.println(add(1,2));

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle methodHandle = lookup.findStatic(MethodHandleMain.class,
                "add",
                MethodType.methodType(int.class, int.class, int.class));
        System.out.println(methodHandle.invoke(1,2));
    }

    private static int add(int x, int y) {
        return x + y;
    }
}
