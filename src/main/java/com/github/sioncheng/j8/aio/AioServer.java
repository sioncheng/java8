package com.github.sioncheng.j8.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AioServer {

    public AioServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        this.shouldStop = false;
        
        AsynchronousChannelGroup asynchronousChannelGroup =
                AsynchronousChannelGroup.withThreadPool(Executors.newSingleThreadExecutor());

        AsynchronousServerSocketChannel serverSocketChannel =
                AsynchronousServerSocketChannel.open(asynchronousChannelGroup).bind(new InetSocketAddress(this.port));
        
        
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {


            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {

                System.out.println(Thread.currentThread().getName());

                try {
                    System.out.println("new connection in " + result.getRemoteAddress().toString());
                } catch (IOException ex) {}

                //processAccepted(result);
                ByteBuffer bf = ByteBuffer.allocate(10);
                result.read(bf, 60, TimeUnit.SECONDS, bf, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer readBytes, ByteBuffer attachment) {
                        if (readBytes.intValue() == -1) {
                            System.out.println("read -1");

                            try {
                                result.close();
                            } catch (Exception ex) {}

                            return;
                        }

                        if (readBytes > 0) {
                            byte[] data = new byte[readBytes];
                            bf.flip();
                            bf.get(data);

                            System.out.println("read data " + new String(data));
                        }

                        if (!shouldStop) {
                            bf.clear();
                            result.read(bf, 60, TimeUnit.SECONDS, bf, this);
                        } else {
                            try {
                                result.close();
                            } catch (Exception ex) {}

                            return;
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        exc.printStackTrace();

                        try {
                            result.close();
                        } catch (Exception ex) { }

                    }
                });


                if (!shouldStop) {
                    serverSocketChannel.accept(null, this);
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();

                if (!shouldStop) {
                    serverSocketChannel.accept(null, this);
                }
            }

        });
    }

    public void stop() {
        this.shouldStop = true;
    }

    
    private volatile boolean shouldStop ;

    private int port;
}
