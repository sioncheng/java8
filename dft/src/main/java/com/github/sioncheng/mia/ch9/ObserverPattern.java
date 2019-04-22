package com.github.sioncheng.mia.ch9;

import java.util.ArrayList;
import java.util.List;

public class ObserverPattern {

    interface Observer {
        void notify(String tweet);
    }

    static class NotifyHelper {
        public static void checkNotify(String tweet, String key, String prefix) {
            if (tweet != null && tweet.contains(key)) {
                System.out.println(prefix + tweet);
            }
        }
    }

    static class NYTimes implements Observer {
        @Override
        public void notify(String tweet) {
            NotifyHelper.checkNotify(tweet,"money", "Breaking new in NY! ");
        }
    }

    static class LeMonde implements Observer {
        @Override
        public void notify(String tweet) {
            NotifyHelper.checkNotify(tweet,"wine", "Today cheese news and wine! ");
        }
    }

    interface Subject {
        void registerObserver(Observer observer);
        void notifyObservers(String tweet);
    }

    static class Feed implements Subject {
        private final List<Observer> observers = new ArrayList<>();

        @Override
        public void registerObserver(Observer observer) {
            observers.add(observer);
        }

        @Override
        public void notifyObservers(final String tweet) {
            observers.forEach(x -> x.notify(tweet));
        }
    }

    public static void main(String[] args) {
        Subject subject = new Feed();
        subject.registerObserver(new NYTimes());
        subject.registerObserver(new LeMonde());

        subject.registerObserver(tweet -> NotifyHelper.checkNotify(tweet,  "queen", "News from London! "));

        subject.notifyObservers("money");
        subject.notifyObservers("wine");
        subject.notifyObservers("queen");
    }
}
