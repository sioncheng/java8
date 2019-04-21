package com.github.sioncheng.mia.ch6;

import com.github.sioncheng.mia.ch4.Dish;
import com.github.sioncheng.mia.ch4.Menu;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Grouping {
    public enum  CaloricLevel {DIET, NORMAL, FAT};

    public static void main(String[] args) {

        Map<Dish.Type, List<Dish>> dishesByType = Menu.DISH_LIST.stream()
                .collect(Collectors.groupingBy(Dish::getType));
        System.out.println(dishesByType);

        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = Menu.DISH_LIST.stream()
                .collect(Collectors.groupingBy(dish -> {
                    if (dish.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    } else if (dish.getCalories() <= 600) {
                        return CaloricLevel.NORMAL;
                    } else {
                        return CaloricLevel.FAT;
                    }
                }));


        System.out.println(dishesByCaloricLevel);
    }
}
