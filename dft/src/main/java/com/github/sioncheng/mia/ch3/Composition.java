package com.github.sioncheng.mia.ch3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Composition {

    static class A {
        private int a1;
        private int a2;

        public A(int a1, int a2) {
            this.a1 = a1;
            this.a2 = a2;
        }

        public int getA1() {
            return a1;
        }

        public void setA1(int a1) {
            this.a1 = a1;
        }

        public int getA2() {
            return a2;
        }

        public void setA2(int a2) {
            this.a2 = a2;
        }

        @Override
        public String toString() {
            return this.a1 + "-" + this.a2;
        }
    }

    static class AUtil {
        public static boolean a2(A a) {
            return at(a, 2);
        }

        public static boolean a3(A a) {
            return at(a, 3);
        }

        public static boolean at(A a, int v) {
            return a.getA1() >= v && a.getA2() >= v;
        }
    }

    public static void main(String... args) {
        Comparator<Integer> integerComparable = Comparator.comparing(x->x*2);

        List<Integer> integerList = Arrays.asList(1,2,3,4);
        integerList.sort(integerComparable);
        System.out.println(integerList);
        integerList.sort(integerComparable.reversed());
        System.out.println(integerList);

        List<A> aList = Arrays.asList(new A(1,2), new A(1,3), new A(1,4),
                new A(2,1), new A(3,1), new A(4,1), new A(3,2), new A(2,2));
        aList.sort(Comparator.comparing(A::getA1));
        System.out.println(aList);

        aList.sort(Comparator.comparing(A::getA1).thenComparing(A::getA2));
        System.out.println(aList);

        Predicate<A> predicate = AUtil::a2;
        System.out.println(predicate.test(new A(1,2)));
        System.out.println(predicate.test(new A(3,3)));
        System.out.println(predicate.negate().test(new A(1,2)));

        Predicate<A> predicate3 = AUtil::a3;
        System.out.println(predicate.and(predicate3).test(new A(1,1)));
        System.out.println(predicate.and(predicate3).test(new A(3,3)));

        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        System.out.println(f.apply(1));
        //g(f(x))
        System.out.println(f.andThen(g).apply(1));
        //f(g(x))
        System.out.println(f.compose(g).apply(1));
    }
}
