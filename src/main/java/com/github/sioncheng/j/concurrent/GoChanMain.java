package com.github.sioncheng.j.concurrent;

public class GoChanMain {

    public static void main(String... args) throws Exception{
        final GoChan<Integer> goChan = new GoChan<>();

        Thread prod = new Thread(()-> {
            for (int i = 0; i < 3; i++) {
                System.out.println("receiver " + goChan.receive());
            }
        });
        prod.setDaemon(true);
        prod.start();

        Thread cons = new Thread(()-> {
            System.out.println("sender");
            for (int i = 0 ; i < 3; i++) {
                goChan.send(i);
            }
        });
        cons.setDaemon(true);
        cons.start();



        Thread.sleep(100);

        System.out.println("ok");
    }
}
