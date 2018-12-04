package com.github.sioncheng.j.io;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MapReduceWordCounter {

    private static Map<String, BufferedOutputStream> mapFiles = new HashMap<>();

    public static void main(String[] args) throws IOException {

        final String path = "/Users/sion/Downloads/PrideAndPrejudice.txt";

        map(path);

    }

    private static void map(String path) throws IOException  {

        FileInputStream fileInputStream = new FileInputStream(new File(path));

        final int bufferSize = 8192;

        byte[] buffer = new byte[bufferSize];

        int n = fileInputStream.read(buffer, 0 , bufferSize);

        final int a = (int)'a';
        final int z = (int)'z';
        final int A = (int)'A';
        final int Z = (int)'Z';


        boolean first = false;
        int s = -1;
        int e = -1;

        while (n != -1) {
            for (int i = 0 ; i < n; i++) {
                byte b = buffer[i];
                if ( (b >= a && b <= z) || (b >= A && b <= Z) ) {
                    if (!first) {
                        first = true;
                        s = i;
                        e = i;
                    } else {
                        e++;
                    }
                } else {
                    if (e >= s && s != -1) {
                        String word = new String(buffer, s, e-s+1).toLowerCase();
                        writeToMapFiles(word);

                        e = s = -1;
                        first = false;
                    }
                }
            }

            int ii = 0;
            if ( s != -1) {
                for (int j = s; j <=e; j++, ii++) {
                    buffer[ii] = buffer[j];
                }
                first = false;
                s = -1;
                e = -1;
            }

            n = fileInputStream.read(buffer, ii, bufferSize - ii) + ii;
        }

        if (e >= s && s != -1) {
            String word = new String(buffer, s, e-s+1).toLowerCase();
            writeToMapFiles(word);
        }

        fileInputStream.close();

        for (Map.Entry<String, BufferedOutputStream> kv :
                mapFiles.entrySet()) {
            kv.getValue().close();
        }
    }

    private static void writeToMapFiles(String word) throws IOException {
        String f = word.substring(0,1);
        BufferedOutputStream fileOutputStream ;
        if (mapFiles.containsKey(f)) {
            fileOutputStream = mapFiles.get(f);
        } else {
            fileOutputStream = new BufferedOutputStream(new FileOutputStream(new File("/Users/sion/Downloads/PrideAndPrejudice-" + f + ".txt")));
            mapFiles.put(f, fileOutputStream);
        }

        fileOutputStream.write(String.format("%s 1%s",word, System.getProperty("line.separator", "\n")).getBytes());
    }
}
