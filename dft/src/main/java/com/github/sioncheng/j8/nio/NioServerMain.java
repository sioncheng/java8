package com.github.sioncheng.j8.nio;

import java.io.IOException;

public class NioServerMain {

    public static void main(String[] args) throws IOException {
        System.out.println("nio server main");

        NioServer nioServer = new NioServer(7000);
        nioServer.start();

        System.in.read();

        nioServer.stop();

        System.out.println("end");
    }
}
