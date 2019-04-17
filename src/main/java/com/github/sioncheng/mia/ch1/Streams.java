package com.github.sioncheng.mia.ch1;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

import static java.util.stream.Collectors.groupingBy;

public class Streams {

    static class Transaction {
        private double price;
        private String corrency;

        public Transaction(){}

        public Transaction(double price, String currency) {
            this.price = price;
            this.corrency = currency;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getCorrency() {
            return corrency;
        }

        public void setCorrency(String corrency) {
            this.corrency = corrency;
        }

        @Override
        public String toString() {
            return this.price + " " + this.corrency;
        }
    }

    public static void main(String... args) {

        List<Transaction> transactions = Arrays.asList(new Transaction(100, "c1"),
                new Transaction(200, "c2"),
                new Transaction(300, "c3"),
                new Transaction(250, "c2"),
                new Transaction(180, "c1"));

        Map<String, List<Transaction>> expensiveTrans = transactions.stream()
                .filter(t->t.getPrice() > 150)
                .collect(groupingBy(Transaction::getCorrency));

        expensiveTrans.forEach((x,y)->{
            System.out.println(x);
            y.forEach(System.out::println);
        });
    }
}
