package com.github.sioncheng.j.generic;

import java.util.ArrayList;
import java.util.List;

public class PECSMain {

    public static void main(String[] args) {
        List<Apple> appleList = new ArrayList<>();
        add(appleList, new Apple());

        List<Fruit> fruitList = new ArrayList<>();
        add(fruitList, new Banana());
        add(fruitList, new Orange());

        List<Object> superList = new ArrayList<>();
        transform(superList, fruitList);
        transform(superList, appleList);

        appleList.stream().forEach(System.out::println);
        System.out.println();

        fruitList.stream().forEach(System.out::println);
        System.out.println();

        superList.stream().forEach(System.out::println);
    }

    static <T> void add(List<? super T> list, T e) {
        list.add(e);
    }

    static <T> void transform(List<? super T> desc, List<? extends T> src) {
        for (T e : src) {
            desc.add(e);
        }
    }
}
