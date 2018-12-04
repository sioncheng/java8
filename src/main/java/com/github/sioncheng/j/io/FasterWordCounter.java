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


    public static volatile boolean taskEnd = false;

    public static void main(String[] args) throws Exception {

        long begin = System.currentTimeMillis();

        final String path = "/Users/sion/Downloads/PrideAndPrejudice.txt";

        File file = new File(path);

        if (file.exists() == false) {
            return;
        }

        final int concurrency = 4 ;

        HashMap<Integer, AtomicInteger> submitted = new HashMap<>();
        for(int i = 0 ; i < concurrency; i++) {
            submitted.put(i, new AtomicInteger(0));
        }

        HashMap<Integer, LinkedBlockingQueue<String>> tasks = new HashMap<>(4);
        for (int i = 0 ; i < concurrency; i++) {
            tasks.put(i, new LinkedBlockingQueue());
        }

        HashMap<Integer, HashMap<String, AtomicInteger>> hashMapHashMap = new HashMap<>(concurrency);
        for(int i = 0 ; i < concurrency; i++) {
            HashMap<String, AtomicInteger> stringIntegerHashMap = new HashMap<>(3000);
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
                            String word = tasks.get(h).poll(100, TimeUnit.MILLISECONDS);
                            if (word == null) {
                                if (taskEnd) {
                                    break;
                                } else {
                                    continue;
                                }
                            }

                            HashMap<String, AtomicInteger> stringIntegerHashMap = hashMapHashMap.get(h);
                            if (stringIntegerHashMap.containsKey(word)) {
                                stringIntegerHashMap.get(word).incrementAndGet();
                            } else {
                                stringIntegerHashMap.put(word, new AtomicInteger(1));
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

        final int a = (int)'a';
        final int z = (int)'z';
        final int A = (int)'A';
        final int Z = (int)'Z';


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
                        String word = new String(buffer, s, e-s+1);
                        int h = word.substring(0, 1).charAt(0) % concurrency;
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
            String word = new String(buffer, s, e-s+1);
            int h = word.substring(0, 1).charAt(0) % concurrency;
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
        for (Map.Entry<Integer, HashMap<String, AtomicInteger>> counter :
                hashMapHashMap.entrySet()) {
            for (Map.Entry<String, AtomicInteger> kv:
                 counter.getValue().entrySet()) {

                fileOutputStream.write(String.format("%s %d \n", kv.getKey(), kv.getValue().intValue()).getBytes());
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
