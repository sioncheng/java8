package com.github.sioncheng.mia.ch6;

import com.github.sioncheng.mia.ch4.Dish;
import com.github.sioncheng.mia.ch4.Menu;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CollectorInterface {

    static class ToListCollector<T> implements Collector<T, List<T>, List<T>>  {
        @Override
        public Supplier<List<T>> supplier() {
            System.out.println("supplier");
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<T>, T> accumulator() {
            System.out.println("accumulator");
            return (list, t) -> {
                System.out.println("accumulator... " + Thread.currentThread().getId());
                list.add(t);
            };
        }

        @Override
        public Function<List<T>, List<T>> finisher() {
            //return Function.identity()
            System.out.println("finisher");
            return x -> x;
        }

        @Override
        public BinaryOperator<List<T>> combiner() {
            System.out.println("combiner");
            return (l1, l2) -> {
                System.out.println("combiner..." + Thread.currentThread().getId());
                l1.addAll(l2);
                return l1;
            };
        }


        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.CONCURRENT));
        }
    }

    public static void main(String[] args) {

        List<Dish> dishList = Menu.DISH_LIST.stream()
                .filter(Dish::isVegetarian)
                .collect(new ToListCollector<>());

        System.out.println(dishList);


        List<Dish> dishList2 = Menu.DISH_LIST.parallelStream()
                .collect(new ToListCollector<>());

        System.out.println(dishList2);

        List<Dish> dishList3 = Menu.DISH_LIST.stream()
                .collect(ArrayList::new, //supplier
                        List::add, //accumulator
                        List::addAll //combiner
                        );

        System.out.println(dishList3);
    }
}
