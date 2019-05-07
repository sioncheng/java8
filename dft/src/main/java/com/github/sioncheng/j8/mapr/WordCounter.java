package com.github.sioncheng.j8.mapr;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class WordCounter {

    static final String LINE_SEPARATOR = System.getProperty("line.separator");

    static class Counter  {
        private int init;
        public Counter(int init) {
            this.init = init;
        }

        public int get() {
            return init;
        }

        public void inc(int v) {
            this.init+=v;
        }
    }

    public static void main(String[] args) throws Exception{
        long begin = System.currentTimeMillis();
        split();
        long ts = System.currentTimeMillis() - begin;
        System.out.println(ts);
    }

    private static void split() throws Exception{
        int numOfThreads = Runtime.getRuntime().availableProcessors() * 2;

        CountDownLatch countDownLatch = new CountDownLatch(numOfThreads);

        final AtomicBoolean end = new AtomicBoolean(false);

        List<ConcurrentLinkedQueue<String>> strings = new ArrayList<>();

        for (int i = 0 ; i < numOfThreads; i++) {
            final ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
            final int fn = i;

            strings.add(q);

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final BufferedWriter writer =
                                new BufferedWriter(new FileWriter(
                                        new File(String.format("%s/word_counter/file-0-%d.txt", System.getProperty("user.home"), fn))));

                        while (end.get() == false) {
                            String s = q.poll();
                            if (s == null) {
                                sleep(100);
                                continue;
                            }

                            writer.write(String.format("%s 1%s", s, LINE_SEPARATOR));
                        }

                        writer.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }

                    try {
                        //


                        boolean accumulated;

                        String countingFile = String.format("%s/word_counter/file-0-%d", System.getProperty("user.home"), fn);

                        int countingTimes = 0;
                        int maxCountingTimes = 10000;

                        for (; ; ) {
                            Map<String, Counter> counters = new HashMap<>();
                            accumulated = false;

                            String str;
                            BufferedReader br = new BufferedReader(new FileReader(countingFile + ".txt"));
                            String nextCountingFile = countingFile + "-c";
                            BufferedWriter bw = new BufferedWriter(new FileWriter(nextCountingFile + ".txt"));
                            while ((str = br.readLine()) != null) {
                                //System.out.println(str);//此时str就保存了一行字符串

                                String[] arr = str.split(" ");
                                //CounterKey counterKey = new CounterKey(arr[0]);
                                if (counters.containsKey(arr[0])) {
                                    counters.get(arr[0]).inc(Integer.parseInt(arr[1]));
                                    accumulated=true;
                                    countingTimes += 1;
                                } else {
                                    counters.put(arr[0], new Counter(Integer.parseInt(arr[1])));
                                }

                                if (countingTimes > maxCountingTimes) {
                                    for (Map.Entry<String, Counter> kv :
                                            counters.entrySet()) {

                                        bw.write(String.format("%s %d%s", kv.getKey(), kv.getValue().get(), LINE_SEPARATOR));
                                    }

                                    countingTimes = 0;
                                    counters = new HashMap<>();
                                }
                            }

                            for (Map.Entry<String, Counter> kv :
                                    counters.entrySet()) {

                                bw.write(String.format("%s %d%s", kv.getKey(), kv.getValue().get(), LINE_SEPARATOR));
                            }

                            br.close();
                            bw.close();



                            new File(countingFile + ".txt").delete();
                            new File(nextCountingFile + ".txt").renameTo(new File(countingFile + ".txt"));


                            if (!accumulated) {
                                break;
                            }

                        }
                    }catch (IOException ioe) {
                        ioe.printStackTrace();
                    }


                    countDownLatch.countDown();
                }
            });

            t.setDaemon(true);

            t.start();
        }

        String path = String.format("%s/word_counter/file1.txt", System.getProperty("user.home"));

        File file = new File(path);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        int buf = 5096;
        char[] block = new char[buf];

        int n = 0;
        int i = 0;
        while ((n = bufferedReader.read(block, i, buf-i)) > 0) {

            int m = 0;
            boolean picked = false;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < n; j++) {
                if (isAscii(block[j])) {
                    picked = true;
                    sb.append(block[j]);
                    m++;
                } else {
                    if (picked) {
                        //
                        //System.out.println(sb.toString());

                        String word = sb.toString().toLowerCase();
                        strings.get(word.length() % numOfThreads).add(word);

                        sb = new StringBuilder();
                        picked = false;
                        m = 0;
                    }
                }
            }

            if (picked) {

                int remains = n - m;
                for (int k = 0; k < m; k++) {
                    block[k] = block[remains+k];
                    i = k;
                }
            } else {
                i = 0;
            }
        }


        end.set(true);

        countDownLatch.await();
    }

    private static boolean isAscii(char c) {
        int cv = (int)c;
        return (cv >= 65 && cv <= 90) || (cv >= 97 && cv <= 122);
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ex) {

        } finally {

        }
    }
}
