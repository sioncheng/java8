package com.github.sioncheng.j.concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class UnsafeParkExam {

    public static void main(String[] args) throws Exception {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                long begin = System.currentTimeMillis();
                System.out.println("park t1 before " + begin);
                unsafe.park(false, TimeUnit.NANOSECONDS.convert(2, TimeUnit.SECONDS));
                long end = System.currentTimeMillis();
                System.out.println("park t1 after " + end + " , " + (end - begin));
            }
        });

        t1.start();

        TimeUnit.SECONDS.sleep(1);

        unsafe.unpark(t1);

        TimeUnit.SECONDS.sleep(2);

        ReentrantLock lock = new ReentrantLock();
        lock.newCondition();
    }
}
