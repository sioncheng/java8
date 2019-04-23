package com.github.sioncheng.mia.ch11;

import java.util.Optional;
import java.util.OptionalInt;

public class NullOptional {

    public static void main(String[] args) {
        OptionalInt optionalInt = OptionalInt.of(1);
        optionalInt.ifPresent(System.out::println);

        Optional<Integer> optionalInteger = Optional.ofNullable(null);
        System.out.println(optionalInteger.orElse(-1));
    }
}
