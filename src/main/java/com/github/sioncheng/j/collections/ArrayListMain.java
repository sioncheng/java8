package com.github.sioncheng.j.collections;

import java.util.ArrayList;
import java.util.List;

public class ArrayListMain {

    public static void main(String[] args) {
        ArrayList<Integer> al = new ArrayList<Integer>(10);
        for (int i = 0 ; i < 10; i++) {
            al.add(i);
        }

        List<Integer> sal = al.subList(0, 5);
        for (int i = 0 ; i < 5; i++) {
            System.out.print(String.format("%5d", al.get(i)));
            sal.set(i, i * i);
        }
        System.out.println();

        for (Integer elem :
                al) {
            System.out.print(String.format("%5d", elem));
        }
        System.out.println();
    }
}
