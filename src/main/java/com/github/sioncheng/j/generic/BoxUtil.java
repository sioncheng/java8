package com.github.sioncheng.j.generic;

public class BoxUtil {

    public static void showBox(Box<?> box) {
        System.out.println(box.get());
    }

    public static void showBoxOfNumbers(Box<? extends Number> ne) {
        System.out.println(ne.get());
    }
}
