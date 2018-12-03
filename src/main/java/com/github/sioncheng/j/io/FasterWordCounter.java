package com.github.sioncheng.j.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class FasterWordCounter {

    public static void main(String[] args) throws Exception {

        long begin = System.currentTimeMillis();

        final String path = "/Users/sion/Downloads/PrideAndPrejudice.txt";

        File file = new File(path);

        if (file.exists() == false) {
            return;
        }

        ConcurrentHashMap<String, Integer> counters =
                new ConcurrentHashMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        FileInputStream fileInputStream = new FileInputStream(file);

        AtomicInteger submitted = new AtomicInteger(0);

        final int bufferSize = 4096;

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
                        submitted.incrementAndGet();
                        count(executorService, word.toLowerCase(), counters, submitted);

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
            count(executorService, word.toLowerCase(), counters, submitted);
        }

        fileInputStream.close();

        while (submitted.get() != 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {

            }

        }

        FileOutputStream fileOutputStream = new FileOutputStream("/Users/sion/count.txt");
        for (Map.Entry<String, Integer> counter :
                counters.entrySet()) {
            //System.out.println(String.format("%s %d", counter.getKey(), counter.getValue()));
            fileOutputStream.write(String.format("%s %d \n", counter.getKey(), counter.getValue()).getBytes());
        }
        fileOutputStream.close();

        executorService.shutdown();

        long ts = System.currentTimeMillis() - begin;

        System.out.println("end " + ts);
    }

    private static void count(ExecutorService executorService,
                              String word,
                              ConcurrentHashMap<String, Integer> counters,
                              AtomicInteger submitted) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                if (counters.containsKey(word)) {
                    counters.put(word, counters.get(word) + 1);
                } else {
                    counters.put(word, 1);
                }

                submitted.decrementAndGet();

            }
        });
    }
}
