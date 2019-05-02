package com.github.sioncheng.j.collections;

import java.util.HashSet;
import java.util.TreeSet;

public class SetMain {

    public static void main(String[] args) {

        HashSet<Integer> integerHashSet = new HashSet<>();
        integerHashSet.add(1);
        integerHashSet.add(3);
        integerHashSet.add(5);

        System.out.println(integerHashSet.contains(1));

        integerHashSet.forEach(System.out::println);

        Object o = new Object();
        o.toString();

        TreeSet<Integer> integerTreeSet = new TreeSet<>();
        integerTreeSet.add(1);
        integerTreeSet.add(5);
        integerTreeSet.add(3);

        integerTreeSet.forEach(System.out::println);

        System.identityHashCode(o);

        int h = 3;
        int size = 16;

        System.out.println(h & (size - 1));
        System.out.println(h % size);
    }
}
