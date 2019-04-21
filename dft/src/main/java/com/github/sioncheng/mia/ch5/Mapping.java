package com.github.sioncheng.mia.ch5;

import com.github.sioncheng.mia.ch4.Dish;
import com.github.sioncheng.mia.ch4.Menu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Mapping {

    public static void main(String... args) {

        List<String> dishNames = Menu.DISH_LIST.stream()
                .map(Dish::getName)
                .collect(Collectors.toList());
        System.out.println(dishNames);

        System.out.println(dishNames.stream().map(String::length).collect(Collectors.toList()));

        List<Integer> dishNamesLength = Menu.DISH_LIST.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println(dishNamesLength);

        List<String> words = Arrays.asList("hello", "world");

        List<String> chars = words.stream().map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(chars);


        List<String> chars2 = words.stream().map(word -> word.split(""))
                .flatMap(x -> Arrays.asList(x).stream())
                .distinct()
                .collect(Collectors.toList());

        System.out.println(chars2);
    }
}
