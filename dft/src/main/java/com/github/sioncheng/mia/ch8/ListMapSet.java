package com.github.sioncheng.mia.ch8;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMapSet {

    public static void main(String[] args) {
        List<String> stringList = Arrays.asList("hello", "world");

        System.out.println(stringList);
        //unsupported operation exception
        //stringList.add("!");

        stringList.removeIf(x -> x.length() == 0);

        stringList.replaceAll(x -> x.toUpperCase());

        System.out.println(stringList);

        Map<String, Integer> maps = new HashMap<>();
        stringList.forEach(x -> maps.put(x, x.length()));

        System.out.println(maps);

        System.out.println(maps.getOrDefault("hello", 0));

    }
}
