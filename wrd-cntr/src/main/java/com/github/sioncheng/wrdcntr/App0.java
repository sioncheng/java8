package com.github.sioncheng.wrdcntr;

import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class App0 {

    static final int threads = Runtime.getRuntime().availableProcessors();
    static final CountDownLatch countDownLatch = new CountDownLatch(threads);

    static final List<LinkedBlockingQueue<BytesWord>> wordQueues =new ArrayList<>();
    static final List<ConcurrentHashMap<BytesWord, AtomicInteger>> wordCounters = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        System.out.println("word counter");

        for(int i = 0; i< threads; i++) {
            wordQueues.add(new LinkedBlockingQueue<>());
            wordCounters.add(new ConcurrentHashMap<>());
        }


        for (int i = 0; i < threads; i++) {
            final int c = i;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            BytesWord s = wordQueues.get(c).take();
                            if (BytesWord.EMPTY == s) {
                                break;
                            }

                            AtomicInteger atomicInteger= wordCounters.get(c).get(s);
                            if (atomicInteger == null) {
                                atomicInteger = new AtomicInteger(0);
                                wordCounters.get(c).put(s, atomicInteger);
                            }

                            atomicInteger.incrementAndGet();
                        }
                    } catch (InterruptedException ie) {

                    }

                    countDownLatch.countDown();
                }
            });

            t.setName("counter-" + i);
            t.start();
        }


        try {
            wordProducer();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            for (LinkedBlockingQueue<BytesWord> queue:
                    wordQueues) {
                queue.put(BytesWord.EMPTY);
            }
        }

        countDownLatch.await();


        for (ConcurrentHashMap<BytesWord, AtomicInteger> wordCounter: wordCounters){
            for (Map.Entry<BytesWord, AtomicInteger> kv :
                    wordCounter.entrySet()) {
                System.out.println(kv.getKey() + " -> " + kv.getValue());
            }
        }
    }

    private static void wordProducer() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(Util.PRIDE_AND_PREJUDICE_TXT);
        FileChannel fileChannel = fileInputStream.getChannel();
        MappedByteBuffer mappedByteBuffer = null;
        final long fileSize = fileChannel.size();

        int defaultBlockSize = 50 * 1024 * 1024;
        long position = 0;
        int size = 0;
        long remains = 0;

        if (fileSize > position + defaultBlockSize) {
            size = defaultBlockSize;
            remains = size;
        } else {
            remains = fileSize - position;
            size = (int)remains;
        }
        while (remains > 0) {
            mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, position, size);

            int p = 0;
            BytesWord sb = new BytesWord();
            TakeCharStatus takeCharStatus = TakeCharStatus.BEGIN;
            for (int i = 0 ; i < size; i++) {
                byte b = mappedByteBuffer.get(i);
                if (Util.isAlpha(b)) {
                    if (takeCharStatus == TakeCharStatus.BEGIN) {
                        takeCharStatus = TakeCharStatus.MIDDLE;
                        p = i;
                    }
                    sb.append(b);
                } else {
                    if (takeCharStatus == TakeCharStatus.BEGIN) {
                        continue;
                    }

                    if (takeCharStatus == TakeCharStatus.MIDDLE) {
                        int n = mappedByteBuffer.get(i - 1) % threads;
                        wordQueues.get(n).put(sb);

                        takeCharStatus = TakeCharStatus.BEGIN;
                        p = 0;
                        sb = new BytesWord();
                    }
                }
            }

            if (p == 0) {
                position = position + size;
            } else {
                position = position + p;
            }

            if (fileSize > position + defaultBlockSize) {
                size = defaultBlockSize;
                remains = size;
            } else {
                remains = fileSize - position;
                size = (int)remains;
            }

        }


        fileChannel.close();
        fileInputStream.close();

    }
}
