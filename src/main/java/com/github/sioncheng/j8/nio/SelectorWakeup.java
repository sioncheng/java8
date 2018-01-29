package com.github.sioncheng.j8.nio;

import java.nio.channels.Selector;

public class SelectorWakeup {

    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
        selector.wakeup();
        //sudo strace -f -e java com.github.sioncheng.j8.nio.SelectorWakeup
    }
}
