package com.github.sioncheng.j8.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NioServer {

    public NioServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        shouldStop = false;
        newSocketChannels = new ConcurrentLinkedQueue<SocketChannel>();

        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(this.port));
        //serverSocketChannel.configureBlocking(false);




        serverThread = new Thread(new Runnable() {
            public void run()  {
                while (!shouldStop) {
                    try {
                        SocketChannel socketChannel = serverSocketChannel.accept();

                        System.out.println(String.format("new client in %s"
                                , socketChannel.socket().getRemoteSocketAddress().toString()));

                        newSocketChannels.add(socketChannel);

                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });

        serverThread.setDaemon(true);
        serverThread.start();

        clientThread = new Thread(new Runnable() {
            public void run() {
                Selector selector;
                try {
                    selector = Selector.open();
                } catch (Exception se) {
                    se.printStackTrace();
                    stop();
                    return;
                }

                while(!shouldStop) {

                    SocketChannel socketChannel = newSocketChannels.poll();

                    if (socketChannel != null) {
                        try {

                            System.out.println(String.format("prepare read write for new channel %s"
                                    , socketChannel.socket().getRemoteSocketAddress().toString()));

                            socketChannel.configureBlocking(false);

                            socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                            try {
                                socketChannel.close();
                            } catch (IOException ioe2) {}
                        }
                    }

                    try {
                        int selected = selector.select(10);
                        if (selected > 0) {
                            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                            Iterator<SelectionKey> selectionKeyIterator = selectionKeySet.iterator();
                            while(selectionKeyIterator.hasNext()) {
                                SelectionKey current = selectionKeyIterator.next();
                                if (current.isReadable()) {
                                    System.out.println(String.format("readable, and writable ? %s"
                                            , Boolean.toString(current.isWritable())));


                                    SocketChannel channel = (SocketChannel)current.channel();
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(10);
                                    int readBytes = 10;
                                    while (readBytes > 0) {
                                        readBytes = channel.read(byteBuffer);
                                        System.out.println(String.format("read bytes %d", readBytes));
                                        byteBuffer.flip();
                                        if (readBytes > 0) {
                                            byte[] bytes = new byte[readBytes];
                                            byteBuffer.get(bytes);
                                            System.out.println("read data " + new String(bytes));
                                        }
                                        byteBuffer.clear();
                                    }

                                    if (readBytes == -1) {
                                        System.out.println("remove ops");
                                        current.interestOps(0);
                                    }
                                }

                                selectionKeyIterator.remove();
                            }
                        }
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });
        clientThread.setDaemon(true);
        clientThread.start();
    }

    public void stop() {
        shouldStop = true;
        serverThread.interrupt();
        clientThread.interrupt();
    }

    private int port;
    private volatile boolean shouldStop;
    private Thread serverThread;
    private Thread clientThread;
    private ConcurrentLinkedQueue<SocketChannel> newSocketChannels;
}


