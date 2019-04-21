package com.github.sioncheng.j.resources;

public class GetResourceMain {

    public static void main(String[] args) {
        System.out.println(GetResourceMain.class.getResource(""));
        System.out.println(GetResourceMain.class.getResource("/"));
        System.out.println(GetResourceMain.class.getClassLoader().getResource(""));
        System.out.println(GetResourceMain.class.getClassLoader().getResource("/"));
        System.out.println(ClassLoader.getSystemResource(""));
        System.out.println(ClassLoader.getSystemResource("/"));
    }
}
