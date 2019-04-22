package com.github.sioncheng.mia.ch9;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TemplateMethod {

    static class Database {
        private static Map<Integer, Customer> customerMap = new HashMap<>();

        static {
            customerMap.put(1, new Customer());
            customerMap.put(2, new Customer());
            customerMap.put(3, new Customer());
        }
        public static Customer query(int id) {
            return customerMap.get(id);
        }
    }

    public static class Customer {

    }

    public static class  OnlineBank {
        public void processCustomer(int id, Consumer<Customer> c) {
            Customer customer = Database.query(id);
            c.accept(customer);
        }
    }

    public static void main(String[] args) {
        OnlineBank onlineBank = new OnlineBank();
        onlineBank.processCustomer(1, c -> {System.out.println("give you a ticket?");});

        onlineBank.processCustomer(2, c -> {System.out.println("give you two tickets?");});
    }
}
