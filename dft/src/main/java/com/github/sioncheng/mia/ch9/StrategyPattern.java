package com.github.sioncheng.mia.ch9;

public class StrategyPattern {

    public interface ValidationStrategy {
        boolean execute(String s);
    }

    public static class IsAllLowerCase implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s != null && s.matches("[a-z]+");
        }
    }

    public static class IsNumeric implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s != null && s.matches("\\d+");
        }
    }

    public static class Validator {
        private final ValidationStrategy strategy;
        public Validator(ValidationStrategy v) {
            this.strategy = v;
        }
        public boolean validate(String s) {
            return strategy.execute(s);
        }
    }

    public static void main(String[] args) {

        Validator numericValidator = new Validator(new IsNumeric());
        boolean b1 = numericValidator.validate("aaaa");
        Validator lowerCaseValidator = new Validator(new IsAllLowerCase ());
        boolean b2 = lowerCaseValidator.validate("bbbb");

        System.out.println(b1);
        System.out.println(b2);

        //use lambdas
        Validator numericValidator1 =
                new Validator(s -> s.matches("[a-z]+"));
        boolean b11 = numericValidator1.validate("aaaa");
        Validator lowerCaseValidator1 =
                new Validator(s -> s.matches("\\d+"));
        boolean b22 = lowerCaseValidator1.validate("bbbb");
        System.out.println(b11);
        System.out.println(b22);
    }
}
