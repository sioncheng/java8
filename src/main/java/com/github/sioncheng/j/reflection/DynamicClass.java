package com.github.sioncheng.j.reflection;

public class DynamicClass {

    public static void main(String[] args) {
        CA ca = new CA();
        System.out.println(ca);

        System.out.println(DC.class.isAssignableFrom(ca.getClass()));
        System.out.println(DC.class.isAssignableFrom(ICA.class));
    }

    public static interface DC {
    } // dynamic class tag interface.
}
