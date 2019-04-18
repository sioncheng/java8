package com.github.sioncheng.mia.ch4;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class DishStreams {

    public static void main(String... args) {
        List<Dish> menu = Menu.DISH_LIST;

        Map<Dish.Type, List<Dish>> dishByTypes = menu.stream().collect(groupingBy(Dish::getType));

        dishByTypes.forEach((x,y)->{
            System.out.println(x);
            System.out.println(y);
        });

        List<String> threeHighCaloriesDish = menu.stream()
                .filter(x -> x.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());

        System.out.println(threeHighCaloriesDish);

        List<String> threeHighestCaloriesDish = menu.stream()
                .sorted(Comparator.comparing(Dish::getCalories).reversed())
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());

        System.out.println(threeHighestCaloriesDish);
    }
}
