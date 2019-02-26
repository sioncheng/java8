package com.github.sioncheng.j.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FasterWordCounter {

    static final int a = (int)'a';
    static final int z = (int)'z';
    static final int A = (int)'A';
    static final int Z = (int)'Z';


    static class CounterItem {

        private byte[] bytes;
        private int count;

        public CounterItem(byte[] bytes) {
            this.bytes = bytes;
            this.count = 0;
            this.toLowercase();
        }

        public void inc() {
            this.count = this.count + 1;
        }

        public int getCount() {
            return this.count;
        }

        private void toLowercase() {
            for (int i = 0 ; i < this.bytes.length; i++) {
                if (this.bytes[i] >= A && this.bytes[i] <= Z) {
                    this.bytes[i] = (byte)(this.bytes[i] + 32);
                }
            }
        }

        @Override
        public int hashCode() {
            int h = 0;
            for (int i = 0 ; i < this.bytes.length; i++) {
                h = h * 37 + this.bytes[i];
            }

            return h;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            CounterItem that = (CounterItem) obj;

            if (that.bytes.length != this.bytes.length) {
                return false;
            }

            for (int i = 0 ; i < that.bytes.length; i++) {
                if (that.bytes[i] != this.bytes[i]) {
                    return false;
                }
            }

            return true;
        }
    }


    public static volatile boolean taskEnd = false;

    public static void main(String[] args) throws Exception {

        long begin = System.currentTimeMillis();

        final String path = "/Users/sion/Downloads/PrideAndPrejudice.txt";
        //final String path = "/Users/sion/Downloads/PrideAndPrejudice-t.txt";

        File file = new File(path);

        if (file.exists() == false) {
            return;
        }

        final int concurrency = 4 ;

        HashMap<Integer, AtomicInteger> submitted = new HashMap<>();
        for(int i = 0 ; i < concurrency; i++) {
            submitted.put(i, new AtomicInteger(0));
        }

        HashMap<Integer, LinkedBlockingQueue<CounterItem>> tasks = new HashMap<>(concurrency);
        for (int i = 0 ; i < concurrency; i++) {
            tasks.put(i, new LinkedBlockingQueue());
        }

        HashMap<Integer, HashMap<CounterItem, CounterItem>> hashMapHashMap = new HashMap<>(concurrency);
        for(int i = 0 ; i < concurrency; i++) {
            HashMap<CounterItem, CounterItem> stringIntegerHashMap = new HashMap<>(3000);
            hashMapHashMap.put(i, stringIntegerHashMap);
        }

        HashMap<Integer, ExecutorService> executorServiceHashMap = new HashMap<>(concurrency);
        for(int i = 0 ; i < concurrency; i++) {
            final int h = i;
            executorServiceHashMap.put(h, Executors.newFixedThreadPool(1));
            executorServiceHashMap.get(h).submit(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            CounterItem word = tasks.get(h).poll(100, TimeUnit.MILLISECONDS);
                            if (word == null) {
                                if (taskEnd) {
                                    break;
                                } else {
                                    continue;
                                }
                            }

                            HashMap<CounterItem, CounterItem> stringIntegerHashMap = hashMapHashMap.get(h);
                            CounterItem c = stringIntegerHashMap.get(word);
                            if (c == null) {
                                word.inc();
                                stringIntegerHashMap.put(word, word);
                            } else {
                                c.inc();
                            }
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }
                        submitted.get(h).decrementAndGet();
                    }
                }
            });
        }


        FileInputStream fileInputStream = new FileInputStream(file);

        final int bufferSize = 8192;

        byte[] buffer = new byte[bufferSize];

        int n = fileInputStream.read(buffer, 0 , bufferSize);



        boolean first = false;
        int s = -1;
        int e = -1;

        while (n != -1) {
            for (int i = 0 ; i < n; i++) {
                byte b = buffer[i];
                if ( (b >= a && b <= z) || (b >= A && b <= Z) ) {
                    if (!first) {
                        first = true;
                        s = i;
                        e = i;
                    } else {
                        e++;
                    }
                } else {
                    if (e >= s && s != -1) {
                        //String word =  new String(buffer, s, e-s+1).toLowerCase();
                        byte[] bytes = new byte[e-s+1];
                        System.arraycopy(buffer, s, bytes, 0, bytes.length);
                        CounterItem word = new CounterItem(bytes);
                        int h = buffer[s] % concurrency;
                        tasks.get(h).add(word);
                        submitted.get(h).incrementAndGet();

                        e = s = -1;
                        first = false;
                    }
                }
            }

            int ii = 0;
            if ( s != -1) {
                for (int j = s; j <=e; j++, ii++) {
                    buffer[ii] = buffer[j];
                }
                first = false;
                s = -1;
                e = -1;
            }

            n = fileInputStream.read(buffer, ii, bufferSize - ii) + ii;
        }

        if (e >= s && s != -1) {
            //String word =  new String(buffer, s, e-s+1).toLowerCase();
            byte[] bytes = new byte[e-s+1];
            System.arraycopy(buffer, s, bytes, 0, bytes.length);
            CounterItem word = new CounterItem(bytes);
            int h = buffer[s] % concurrency;
            tasks.get(h).add(word);
            submitted.get(h).incrementAndGet();
        }

        fileInputStream.close();

        taskEnd = true;

        boolean counterEnd = false;
        while (!counterEnd) {
            counterEnd = true;
            for (Map.Entry<Integer, AtomicInteger> kv: submitted.entrySet()){
                if (kv.getValue().get() != 0) {
                    counterEnd = false;
                    break;
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {

            }

        }

        BufferedOutputStream fileOutputStream =
                new BufferedOutputStream(new FileOutputStream("/Users/sion/count.txt"));
        for (Map.Entry<Integer, HashMap<CounterItem, CounterItem>> counter :
                hashMapHashMap.entrySet()) {
            for (Map.Entry<CounterItem, CounterItem> kv:
                 counter.getValue().entrySet()) {

                String w = new String(kv.getKey().bytes);

                fileOutputStream.write(String.format("%s %d \n", w, kv.getValue().getCount()).getBytes());
            }
        }
        fileOutputStream.close();

        for (Map.Entry<Integer, ExecutorService> kv:
                executorServiceHashMap.entrySet()) {
            kv.getValue().shutdown();
        }

        long ts = System.currentTimeMillis() - begin;

        System.out.println("end " + ts);
    }



}
