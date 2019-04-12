package com.github.sioncheng.j.concurrent;

public class GoChanMain {

    public static void main(String... args) throws Exception{
        //final GoChan<Integer> goChan = new GoChanOne<>();
        final GoChan<Integer> goChan = new GoChanBuffered<>(1);

        Thread consumer = new Thread(()-> {
            for (int i = 0; i < 3; i++) {
                System.out.println("receiver " + goChan.receive());
            }
        });
        consumer.setDaemon(true);
        consumer.start();

        Thread consumer2 = new Thread(()-> {
            for (int i = 0; i < 3; i++) {
                System.out.println("receiver2 " + goChan.receive());
            }
        });
        consumer2.setDaemon(true);
        //consumer2.start();

        Thread producer = new Thread(()-> {
            for (int i = 0 ; i < 6; i++) {
                System.out.println("begin send " + i);
                goChan.send(i);
                System.out.println("finish send " + i);
            }
        });
        producer.setDaemon(true);
        producer.start();



        Thread.sleep(5000);

        System.out.println("ok");
    }
}
