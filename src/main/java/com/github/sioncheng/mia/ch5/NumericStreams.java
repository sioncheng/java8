package com.github.sioncheng.mia.ch5;

import com.github.sioncheng.mia.ch4.Dish;
import com.github.sioncheng.mia.ch4.Menu;

import java.util.stream.IntStream;

public class NumericStreams {

    public static void main(String[] args) {
        System.out.println(Menu.DISH_LIST.stream().map(Dish::getCalories).reduce(Integer::sum));
        System.out.println(Menu.DISH_LIST.stream().mapToInt(Dish::getCalories).sum());
        System.out.println(Menu.DISH_LIST.stream().mapToInt(Dish::getCalories).max());

        IntStream intStream = IntStream.rangeClosed(1,3);
        intStream.forEach(System.out::println);
        IntStream.range(1,3).forEach(System.out::println);
    }
}
