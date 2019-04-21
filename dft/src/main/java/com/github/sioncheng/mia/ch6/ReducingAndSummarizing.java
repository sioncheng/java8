package com.github.sioncheng.mia.ch6;

import com.github.sioncheng.mia.ch4.Dish;
import com.github.sioncheng.mia.ch4.Menu;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReducingAndSummarizing {

    public static void main(String[] args) {

        long howManyDishes = Menu.DISH_LIST.stream()
                .collect(Collectors.counting());
        System.out.println(howManyDishes);

        System.out.println(Menu.DISH_LIST.stream().count());

        Optional<Dish> maxCalories = Menu.DISH_LIST.stream()
                .collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)));
        maxCalories.ifPresent(System.out::println);

        System.out.println(Menu.DISH_LIST.stream().collect(Collectors.summarizingInt(Dish::getCalories)));
        System.out.println(Menu.DISH_LIST.stream().collect(Collectors.summingInt(Dish::getCalories)));
        System.out.println(Menu.DISH_LIST.stream().collect(Collectors.reducing(0, Dish::getCalories, (a,b) -> a+b)));

        System.out.println(Menu.DISH_LIST.stream().map(Dish::getName).collect(Collectors.joining(" , ")));
    }
}
