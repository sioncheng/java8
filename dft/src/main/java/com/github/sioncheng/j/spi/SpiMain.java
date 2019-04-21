package com.github.sioncheng.j.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiMain {

    public static void main(String[] args) {
        ServiceLoader<ServiceA> sa = ServiceLoader.load(ServiceA.class);
        Iterator<ServiceA> serviceAIterator = sa.iterator();
        while(serviceAIterator.hasNext()) {
            System.out.println(serviceAIterator.next().methodA("a"));
        }
    }
}
