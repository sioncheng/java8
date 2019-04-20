package com.github.sioncheng.mia.ch6;

import com.github.sioncheng.mia.ch4.Dish;
import com.github.sioncheng.mia.ch4.Menu;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Partition {

    public static void main(String[] args) {

        Map<Boolean, List<Dish>> booleanListMap = Menu.DISH_LIST.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian));

        System.out.println(booleanListMap);
    }
}
