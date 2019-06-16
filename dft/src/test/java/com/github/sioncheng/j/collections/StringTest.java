package com.github.sioncheng.j.collections;

import org.junit.Test;

import java.nio.channels.Selector;

public class StringTest {

    @Test
    public void blankTest() throws Exception{
        System.out.println(isBlank("hello"));
        System.out.println(isBlank(null));
        System.out.println(isBlank(""));

        Selector selector = Selector.open();

        selector.select();

        selector.wakeup();
    }

    private boolean isBlank(String s) {
        return s == null || s.isEmpty();
    }
}
