package com.github.sioncheng.j.pecs;

import java.util.ArrayList;
import java.util.List;

public class PescMain {

    public static void main(String[] args) {

        List<Fruit> lf = new ArrayList<Fruit>();
        lf.add(new Apple());
        lf.add(new Orange());
        for (Fruit f :
                lf) {
            System.out.print(String.format("%s ", f));
        }
        System.out.println();

        List<? extends Fruit> lf2 = collectApples();
        for (Fruit f :
                lf2) {
            System.out.print(String.format("%s ", f));
        }
        System.out.println();

        lf2 = collectOranges();
        for (Fruit f :
                lf2) {
            System.out.print(String.format("%s ", f));
        }
        System.out.println();

        List<Fruit> lf3 = new ArrayList();
        putApple(lf3);
        putOrange(lf3);
        for (Fruit fruit: lf3) {
            System.out.print(String.format("%s ", fruit));
        }
    }

    private static List<Apple> collectApples() {
        List<Apple> appleList = new ArrayList<Apple>();
        for (int i = 0 ; i < 10; i++) {
            appleList.add(new Apple());
        }

        return appleList;
    }

    private static List<Orange> collectOranges() {
        List<Orange> orangeList = new ArrayList<Orange>();
        for(int i = 0 ; i < 10 ; i++) {
            orangeList.add(new Orange());
        }

        return orangeList;
    }

    private static void putApple(List<? super Apple> apples) {
        apples.add(new Apple());
    }

    private static void putOrange(List<? super Orange> objects) {
        objects.add(new Orange());
    }}
