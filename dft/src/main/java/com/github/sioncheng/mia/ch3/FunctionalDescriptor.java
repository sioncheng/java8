package com.github.sioncheng.mia.ch3;

import java.util.function.*;

public class FunctionalDescriptor {

    @FunctionalInterface
    interface Action {
        void doAction();
    }

    static class A {
        public static boolean integerTest(Integer i) {
            return i % 2 == 0;
        }

        public boolean intTest(int i) {
            return i % 2 == 0;
        }
    }

    static class B {
        private int b;
        public B(int b) {
            this.b = b;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }
    }

    public static void main(String... args) {
        Action a = ()->{};
        a.doAction();

        Predicate<Integer> even = x -> x % 2 == 0;
        System.out.println(even.test(1));

        Consumer<Integer> consumer = x -> System.out.println(x * 2);
        consumer.accept(1);

        Function<Integer, Integer> function = x -> x * 2;
        Consumer<Integer> consumer1 = x->System.out.println(function.apply(x));

        consumer1.accept(2);

        IntPredicate intPredicate = x -> x % 2 == 0;
        System.out.println(intPredicate.test(1));

        Predicate<Integer> even1 = A::integerTest;
        System.out.println(even1.test(1));

        IntPredicate intPredicate1 = (new A())::intTest;
        System.out.println(intPredicate1.test(1));

        Supplier<A> aSupplier = A::new;
        System.out.println(aSupplier.get().intTest(1));

        Supplier<Integer> integerSupplier = () -> 1;
        System.out.println(integerSupplier.get());

        Function<Integer, B> bFunction = B::new;
        System.out.println(bFunction.apply(1).getB());

        Function<Integer, B> bFunction1 = i -> new B(i);
        System.out.println(bFunction1.apply(2).getB());
    }
}
