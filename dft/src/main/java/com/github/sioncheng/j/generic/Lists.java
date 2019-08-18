package com.github.sioncheng.j.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lists {

    public static void main(String[] args) {
        toList(1,2,3).forEach(System.out::println);
        Arrays.asList(1,2,3).forEach(System.out::println);

        assert  1 == 1;

        List<Number> numberList = new ArrayList<>();
        List<Integer> integerList = toList(1,2,3);
        copy(numberList, integerList);
        numberList.forEach(System.out::println);
    }

    public static <T> List<T> toList(T... arr) {
        List<T> result = new ArrayList<>();
        for (T a :
             arr) {
            result.add(a);
        }

        return result;
    }

    public static <T> void copy(List<? super T> dst, List<? extends T> src) {
        dst.addAll(src);
    }
}
