package com.github.sioncheng.j8.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class GatherScatterChannel {

    public static void main(String[] args) throws IOException {
        if (args[0].equalsIgnoreCase("r")) {
            scatterFile(args[1]);
        } else {
            gatherFile(args[1]);
        }
    }


    private static void scatterFile(String filepath) throws  IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(filepath, "r");

        FileChannel fileChannel = randomAccessFile.getChannel();

        ByteBuffer buffer1 = ByteBuffer.allocate(10);
        ByteBuffer buffer2 = ByteBuffer.allocate(1000);

        ByteBuffer[] buffers = new ByteBuffer[]{buffer1, buffer2};

        long l = fileChannel.read(buffers);

        System.out.println(String.format("have read %d bytes", l));

        Charset charset = Charset.forName("UTF8");

        buffer1.flip();
        System.out.println(charset.decode(buffer1));

        buffer2.flip();
        System.out.println(charset.decode(buffer2));

        fileChannel.close();
    }

    private static void gatherFile(String filepath) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(filepath, "rw");

        FileChannel fileChannel = randomAccessFile.getChannel();

        ByteBuffer buffer1 = ByteBuffer.wrap("hello".getBytes());
        ByteBuffer buffer2 = ByteBuffer.wrap("world".getBytes());
        ByteBuffer buffer3 = ByteBuffer.wrap(System.getProperties().getProperty("line.separator").getBytes());

        long l = fileChannel.write(new ByteBuffer[]{buffer1, buffer2, buffer3});

        System.out.println(String.format("have wrote %d bytes", l));

        fileChannel.close();
    }
}
