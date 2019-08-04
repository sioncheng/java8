package com.github.sioncheng.wrdcntr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class App1 {

    private static final int processors = Runtime.getRuntime().availableProcessors();

    private static final CountDownLatch waiter = new CountDownLatch(processors);

    private static final List<HashMap<BytesWord, AtomicInteger>> counterList =
            new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws Exception {
        //split
        //count parallel
        //join

        split();
        countParallel();
        join();
    }

    private static void split() throws Exception {

        System.out.println("split");

        String source = "/Users/sion/app/2.txt";

        RandomAccessFile randomAccessFile = new RandomAccessFile(source, "r");
        FileChannel fileChannel = randomAccessFile.getChannel();

        long size = fileChannel.size();
        long block = size / processors;

        long position = 0;
        int p;
        for (p = 0; p < processors - 1; p++) {
            MappedByteBuffer mappedByteBuffer =
                    fileChannel.map(FileChannel.MapMode.READ_ONLY, position + block, 100);

            int i;
            for ( i = 0; i < 100; i++) {
                byte b = mappedByteBuffer.get(i);
                if (!Util.isAlpha(b)) {
                    break;
                }
            }

            if (i == 99) {
                //
                throw new Exception("99");
            }

            String target = "1_" + p + ".txt";
            RandomAccessFile outputStream = new RandomAccessFile(target, "rw");
            FileChannel targetChannel = outputStream.getChannel();

            fileChannel.transferTo(position, block + i, targetChannel);

            targetChannel.close();
            outputStream.close();

            position = position + block + i;
        }

        String target = "1_" + p + ".txt";
        RandomAccessFile outputStream = new RandomAccessFile(target, "rw");
        FileChannel targetChannel = outputStream.getChannel();

        fileChannel.transferTo(position, size - position, targetChannel);

        targetChannel.close();
        outputStream.close();

        fileChannel.close();
        randomAccessFile.close();
    }

    private static void countParallel() {
        for (int i = 0; i < processors; i++) {
            final int c = i;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    HashMap<BytesWord, AtomicInteger> wordAtomicIntegerHashMap = new HashMap<>();

                    try {
                        RandomAccessFile randomAccessFile = new RandomAccessFile("1_" + c + ".txt", "r");

                        TakeCharStatus status = TakeCharStatus.BEGIN;
                        BytesWord bytesWord = new BytesWord();
                        while (true) {
                            byte b = randomAccessFile.readByte();
                            if (Util.isAlpha(b)) {
                                bytesWord.append(b);
                                if (status == TakeCharStatus.BEGIN) {
                                    status = TakeCharStatus.MIDDLE;
                                }
                            } else {
                                if (status == TakeCharStatus.MIDDLE) {
                                    AtomicInteger atomicInteger = wordAtomicIntegerHashMap.get(bytesWord);
                                    if (atomicInteger == null) {
                                        atomicInteger = new AtomicInteger(1);
                                        wordAtomicIntegerHashMap.put(bytesWord, atomicInteger);
                                    } else {
                                        atomicInteger.incrementAndGet();
                                    }

                                    status = TakeCharStatus.BEGIN;
                                    bytesWord = new BytesWord();
                                }
                            }

                            if (b < 0) {
                                break;
                            }
                        }
                        
                        randomAccessFile.close();
                        
                        counterList.add(wordAtomicIntegerHashMap);


                    } catch (Exception ex) {

                    } finally {
                        waiter.countDown();
                    }

                    System.out.println("count 1_" + c + ".txt");
                }
            });

            t.start();
        }
    }

    private static void join() throws Exception {
        waiter.await();

        HashMap<BytesWord, AtomicInteger> wordAtomicIntegerHashMap = new HashMap<>();

        for (int i = 0; i < counterList.size(); i++) {

            HashMap<BytesWord, AtomicInteger> counter = counterList.get(i);

            for (Map.Entry<BytesWord, AtomicInteger> kv :
                    counter.entrySet()) {


                AtomicInteger atomicInteger = wordAtomicIntegerHashMap.get(kv.getKey());
                if (atomicInteger == null) {
                    atomicInteger = kv.getValue();
                    wordAtomicIntegerHashMap.put(kv.getKey(), atomicInteger);
                } else {
                    atomicInteger.addAndGet(kv.getValue().get());
                }
            }




        }


        RandomAccessFile randomAccessFile1 = new RandomAccessFile("r_r_.txt", "rw");
        for (Map.Entry<BytesWord , AtomicInteger> kv :
                wordAtomicIntegerHashMap.entrySet()) {
            String c = kv.getKey().toString() + " " + kv.getValue().toString() + System.getProperty("line.separator");

            randomAccessFile1.write(c.getBytes());
        }
        randomAccessFile1.close();

        System.out.println("join");
    }
}
