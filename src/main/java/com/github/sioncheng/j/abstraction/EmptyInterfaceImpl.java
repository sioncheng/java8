package com.github.sioncheng.j.abstraction;

public class EmptyInterfaceImpl implements EmptyInterface {

    public static void main(String[] args) {

        System.out.println(EmptyInterface.class.isAssignableFrom(EmptyInterfaceImpl.class));
    }
}
