package com.github.sioncheng.mia.ch9;

import com.github.sioncheng.mia.ch4.Dish;
import com.github.sioncheng.mia.ch4.Menu;
import com.github.sioncheng.mia.ch6.Grouping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnonymousClassToLambdaExpr {

    static class DishHelper {
        public static Grouping.CaloricLevel getCaloricLevel(Dish dish) {
            if (dish.getCalories() <= 400) {
                return Grouping.CaloricLevel.DIET;
            } else if (dish.getCalories() <= 600) {
                return Grouping.CaloricLevel.NORMAL;
            } else {
                return Grouping.CaloricLevel.FAT;
            }
        }
    }

    public static void main(String[] args) {
        /*Map<Grouping.CaloricLevel, List<Dish>> dishesByCaloricLevel = Menu.DISH_LIST.stream()
                .collect(Collectors.groupingBy(dish -> {
                    if (dish.getCalories() <= 400) {
                        return Grouping.CaloricLevel.DIET;
                    } else if (dish.getCalories() <= 600) {
                        return Grouping.CaloricLevel.NORMAL;
                    } else {
                        return Grouping.CaloricLevel.FAT;
                    }
                }));


        System.out.println(dishesByCaloricLevel);*/

        Map<Grouping.CaloricLevel, List<Dish>> dishesByCaloricLevel = Menu.DISH_LIST.stream()
                .collect(Collectors.groupingBy( DishHelper::getCaloricLevel));
        System.out.println(dishesByCaloricLevel);
    }
}
