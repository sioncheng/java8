package com.github.sioncheng.jmh.mia;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(jvmArgs={"-Xms4G", "-Xmx4G"})
public class ParallelStreamBenchmark {

    private static final long N = 1_000_000L;

    @Benchmark
    public void sequentialSum() {
        Stream.iterate(1L, i -> i+1).limit(N)
                .reduce(0L, Long::sum);
    }
}
