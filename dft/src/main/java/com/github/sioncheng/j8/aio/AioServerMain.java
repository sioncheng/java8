package com.github.sioncheng.j8.aio;

import java.io.IOException;

public class AioServerMain {

    public static void main(String[] args) throws IOException {
        AioServer aioServer = new AioServer(7000);
        aioServer.start();

        System.in.read();

        aioServer.stop();
    }
}
