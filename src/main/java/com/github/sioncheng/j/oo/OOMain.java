package com.github.sioncheng.j.oo;


import java.util.Arrays;
import java.util.List;

public class OOMain {


    private int b = 1;
    private int a = getB();

    private int getB(){
        return b;
    }

    public static void main(String[] args) {
        System.out.println(new OOMain().a);
    }




}