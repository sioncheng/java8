package com.github.sioncheng.mia.ch9;

import java.util.function.UnaryOperator;

public class ChainOfResponsibility {

    static abstract class Processor<T> {
        protected Processor<T> successor;

        public void setSuccessor(Processor<T> successor) {
            this.successor = successor;
        }

        public T process(T t) {
            T r = handle(t);
            if (successor != null) {
                r = successor.handle(r);
            }

            return r;
        }

        abstract T handle(T t);
    }

    static class HeadProcessor extends Processor<String> {
        @Override
        String handle(String s) {
            return s;
        }
    }

    static class SpellingProcessor extends Processor<String> {
        @Override
        String handle(String s) {
            if (s != null) {
                s = s.replaceAll("lamdba", "lambda");
            }

            return s;
        }
    }

    public static void main(String[] args) {
        HeadProcessor headProcessor = new HeadProcessor();
        SpellingProcessor spellingProcessor = new SpellingProcessor();
        headProcessor.setSuccessor(spellingProcessor);

        System.out.println(headProcessor.process("lamdba awesome"));

        UnaryOperator<String> headProcessor1 = x -> x;
        UnaryOperator<String> spellingProcessor1 = x -> {
          if (x != null) {
              return x.replaceAll("lamdba", "lambda");
          } else{
              return x;
          }
        };

        System.out.println(headProcessor1.andThen(spellingProcessor1).apply("lamdba awesome"));
    }
}
