package com.vergilyn.examples.json.jackson.jmh;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.testng.Assert;

/**
 * @author vergilyn
 * @date 2020-05-07
 */
@State(Scope.Thread)
@Slf4j
public class JacksonJMH {
    private static final ObjectMapper SINGLETON_OBJECT_MAPPER = new ObjectMapper();
    private static final AtomicInteger SINGLETON_INDEX = new AtomicInteger(0);
    private static final AtomicInteger PROTOTYPE_INDEX = new AtomicInteger(0);
    private static final Options OPT = new OptionsBuilder()
            .include(JacksonJMH.class.getSimpleName())
            .threads(10)
            .forks(1)
            .warmupIterations(5)
            .warmupBatchSize(1000)
            .measurementIterations(10)
            .measurementBatchSize(1000)
            .timeUnit(TimeUnit.MILLISECONDS)
            .mode(Mode.SingleShotTime)
            .build();

    /*
      <a href="https://blog.csdn.net/luckyman98/article/details/104082170">单例模式和多例模式的性能测试，每个模式都测试两轮（单线程，多线程)</a>
      模式	    单线程耗时(ms)	多线程耗时(ms)
      单例模式	19.33	        99.66
      多例模式	137.66	        186.33

      threads = 10
      Benchmark             Mode  Cnt   Score    Error  Units
      JacksonJMH.prototype    ss   10  77.615 ± 53.842  ms/op
      JacksonJMH.singleton    ss   10   1.605 ±  1.278  ms/op

      threads = 1
      Benchmark             Mode  Cnt   Score    Error  Units
      JacksonJMH.prototype    ss   10  66.557 ± 16.431  ms/op
      JacksonJMH.singleton    ss   10   1.760 ±  1.577  ms/op
     */
    public static void main(String[] args) throws RunnerException {
        new Runner(OPT).run();
    }

    @TearDown
    public void tearDown(){
        int excepted = OPT.getWarmupIterations().get() * OPT.getWarmupBatchSize().get()
                + OPT.getMeasurementIterations().get() * OPT.getMeasurementBatchSize().get();

        excepted = excepted * OPT.getThreads().get();

        if (SINGLETON_INDEX.get() > 0){
            Assert.assertEquals(SINGLETON_INDEX.get(), excepted);
        }

        if (PROTOTYPE_INDEX.get() > 0){
            Assert.assertEquals(PROTOTYPE_INDEX.get(), excepted);
        }

    }

    /**
     * jackson 单线程，单例ObjectMapper
     */
    @Benchmark
    public void singleton(){
        writeValueAsString(SINGLETON_OBJECT_MAPPER, SINGLETON_INDEX);
    }

    /**
     * jackson 单线程，多例ObjectMapper
     */
    @Benchmark
    public void prototype(){
        writeValueAsString(new ObjectMapper(), PROTOTYPE_INDEX);
    }

    private void writeValueAsString(ObjectMapper objectMapper, AtomicInteger index){
        try {
            objectMapper.writeValueAsString(new JmhDto(index.incrementAndGet(), String.valueOf(index.get())));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class JmhDto {
        private Integer id;
        private String str;
    }
}
