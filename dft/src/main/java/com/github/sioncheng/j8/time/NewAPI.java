package com.github.sioncheng.j8.time;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NewAPI {

    public static void main(String[] args) {
        LocalDate localDate = LocalDate.of(2019,5,20);
        LocalDate localDate1 = LocalDate.now();

        System.out.println(localDate.compareTo(localDate1));

        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        Duration duration = Duration.ofSeconds(1000);
        System.out.println(duration);
    }
}
