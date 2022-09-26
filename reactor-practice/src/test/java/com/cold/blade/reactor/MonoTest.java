package com.cold.blade.reactor;

import java.util.Objects;

import org.junit.Test;

import com.cold.blade.BaseTest;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

/**
 * @author cold_blade
 * @date 2022/9/23
 * @version 1.0
 */
public class MonoTest extends BaseTest {

    @Test
    public void test() {
        Mono<Integer> intFunc = Mono.empty();
        Mono<Long> longFunc = Mono.fromCallable(() -> 0L);
        Mono<Tuple2<Integer, Long>> result = Mono.zip(intFunc, longFunc);
        result.doOnSuccess(this::consume).block();

    }

    private void consume(Tuple2<Integer, Long> tuple2) {
        Integer intVal = tuple2.getT1();
        Long valVal = tuple2.getT2();
        valVal += intVal;
    }
}
