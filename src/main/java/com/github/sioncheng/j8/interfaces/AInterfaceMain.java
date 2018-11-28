package com.github.sioncheng.j8.interfaces;

public class AInterfaceMain implements AInterface {

    @Override
    public void methodA() {
        System.out.println(getA() * getA());
    }

    @Override
    public int getA() {
        return -1;
    }

    public static void main(String[] args) {
        AInterface aInterface = new AInterfaceMain();
        aInterface.methodA();
    }
}
