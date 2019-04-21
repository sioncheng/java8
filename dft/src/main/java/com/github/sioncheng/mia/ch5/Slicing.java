package com.github.sioncheng.mia.ch5;

import java.util.ArrayList;
import java.util.List;

public class Slicing {

    public static void main(String... args) {
        List<Integer> integerList = new ArrayList<>(10);
        for (int i = 0 ; i < 10; i++) {
            integerList.add(i);
        }

        integerList.stream()
                .skip(2)
                .limit(4)
                .forEach(System.out::println);
    }
}
