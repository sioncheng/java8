package com.github.sioncheng.mia.ch17;

import com.github.sioncheng.mia.ch16.Util;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import java.util.concurrent.TimeUnit;

public class SimpleUse {
    public static void main(String[] args) {

        Consumer<Object> consumer = (x) ->
            System.out.println(String.format("%s %d", x, System.currentTimeMillis()));

        Observable.just("a","b","c").subscribe(consumer);

        Observable.interval(1, TimeUnit.SECONDS).blockingSubscribe(consumer);


        //Util.delay(1000000);
    }
}
