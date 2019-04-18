package com.github.sioncheng.mia.ch5;

import com.github.sioncheng.mia.ch4.Dish;
import com.github.sioncheng.mia.ch4.Menu;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class Finding {

    public static void main(String[] args) {
        System.out.println(Menu.DISH_LIST.stream().anyMatch(dish -> dish.getCalories() > 100));
        System.out.println(Menu.DISH_LIST.stream().allMatch(dish -> dish.getCalories() < 1000));
        System.out.println(Menu.DISH_LIST.stream().noneMatch(dish -> dish.getCalories() > 1000));

        System.out.println(Menu.DISH_LIST.stream().filter(Dish::isVegetarian).map(Dish::getName).collect(Collectors.toList()));

        Optional<Dish> anyVegetarian = Menu.DISH_LIST.stream()
                .filter(Dish::isVegetarian)
                .findAny();
        anyVegetarian.ifPresent(x -> System.out.println(x.getName()));

        Arrays.asList(1,2,3).stream().findFirst().ifPresent(System.out::println);
        Arrays.stream(new int[]{1,2,3}).findFirst().ifPresent(System.out::println);

        System.out.println(Arrays.asList(1,2,3,4).stream().reduce(0, (x,y) -> x + y));
    }
}
