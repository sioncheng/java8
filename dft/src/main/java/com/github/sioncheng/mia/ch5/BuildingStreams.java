package com.github.sioncheng.mia.ch5;

import java.util.stream.Stream;

public class BuildingStreams {

    public static void main(String[] args) {
        Stream.of(1,2,3,4,5).forEach(System.out::println);
        Stream.iterate(0, n -> n + 1).limit(10).forEach(System.out::println);
        Stream.generate(System::nanoTime).limit(10).forEach(System.out::println);
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }
}
