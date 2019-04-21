package com.github.sioncheng.j.management;

import java.lang.management.ManagementFactory;

public class ManagementMain {

    public static void main(String[] args) throws Exception{
        System.out.println(ManagementFactory.getRuntimeMXBean().getName());

        System.in.read();
    }

}
