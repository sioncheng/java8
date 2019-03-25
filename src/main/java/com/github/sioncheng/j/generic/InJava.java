package com.github.sioncheng.j.generic;

import java.util.ArrayList;

public class InJava {

    static class MyArrayList<E> {
        public void add(E e) {
            System.out.println(e.getClass());
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);

        for (Integer i :
                arrayList) {
            System.out.println(i);
        }

        MyArrayList<String> s = new MyArrayList<>();
        s.add("s");
    }
}
