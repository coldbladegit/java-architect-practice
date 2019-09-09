package com.cold.blade.reactor;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import com.cold.blade.BaseTest;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;
import reactor.test.StepVerifier;

/**
 * @version 1.0
 */
public class FluxTest extends BaseTest {

    private Flux<String> source;

    @Override
    public void setUp() {
        super.setUp();

        source = Flux.generate(
            AtomicInteger::new,
            (state, sink) -> {
                String suffix = String.valueOf(state.getAndIncrement());
                if (state.get() == 3) {
                    sink.error(new IllegalArgumentException("boom"));
                }
                sink.next("test_".concat(suffix));
                return state;
            });
    }

    @Test
    public void testAppendBoomError() {
        StepVerifier.create(source)
            .expectNext("test_0")
            .expectNext("test_1")
            .expectErrorMessage("boom")
            .verify();
    }

    @Test
    public void testStreamReduce() {
        IntStream integerStream = IntStream.rangeClosed(1, 5);
        Assert.assertTrue(15 == integerStream.reduce(0, Integer::sum));
    }

    @Test
    public void testVirtualTime() {
        StepVerifier.withVirtualTime(() -> Mono.delay(Duration.ofDays(1)))
            .expectSubscription()
            .expectNoEvent(Duration.ofDays(1))
            .expectNext(0L)
            .verifyComplete();
    }

    @Test
    public void testStackTrace() {
        Mono<String> urls = Flux.just("url_1", "url_2")
            .checkpoint("single operator checkpoint")
            .map(String::toUpperCase)
            .take(1L).single();
        StepVerifier.create(urls)
            .expectNext("URL_1")
            .verifyComplete();
    }

    @Test
    public void testTransform() {
        Function<Flux<String>, Flux<String>> filterAndMap = colorFlux -> colorFlux
            .filter(color -> !color.equals("red"))
            .map(String::toUpperCase);
        Flux<String> colors = Flux.just("red", "blue", "yellow")
            .doOnNext(System.out::println)
            .transform(filterAndMap);
        StepVerifier.create(colors)
            .expectNext("BLUE")
            .expectNext("YELLOW")
            .verifyComplete();
    }

    @Test
    public void testCompose() {
        AtomicInteger state = new AtomicInteger();
        Function<Flux<String>, Flux<String>> filterAndMap = colorFlux -> {
            int stateValue = state.getAndIncrement();
            if (0 == stateValue) {
                return colorFlux
                    .filter(color -> !color.equals("red"))
                    .map(String::toUpperCase);
            } else if (1 == stateValue) {
                return colorFlux
                    .filter(color -> !color.equals("blue"))
                    .map(String::toUpperCase);
            }
            return colorFlux
                .filter(color -> !color.equals("yellow"))
                .map(String::toUpperCase);
        };

        Flux<String> colors = Flux.just("red", "blue", "yellow")
            .doOnNext(System.out::println)
            .compose(filterAndMap);
        colors.subscribe(color -> System.out.println("subscriber 1 : ".concat(color)));
        colors.subscribe(color -> System.out.println("subscriber 2 : ".concat(color)));
        colors.subscribe(color -> System.out.println("subscriber 3 : ".concat(color)));
    }

    @Test
    public void testHotPublish() {
        UnicastProcessor<String> source = UnicastProcessor.create();
        Flux<String> colors = source.publish()
            .autoConnect()
            .map(String::toUpperCase);
        colors.subscribe(color -> System.out.println("subscriber 1 : ".concat(color)));
        source.onNext("red");
        colors.subscribe(color -> System.out.println("subscriber 2 : ".concat(color)));
        source.onNext("blue");
        source.onNext("yellow");
        source.onComplete();
    }

    @Test
    public void testConnectableFlux() {
        Flux<Integer> source = Flux.range(1, 5);
        ConnectableFlux<Integer> connectableFlux = source.publish();

        connectableFlux.subscribe(i -> System.out.println("subscriber 1: " + i));
        connectableFlux.subscribe(i -> System.out.println("subscriber 2: " + i));
        connectableFlux.connect();
        System.out.println("start connecting");
    }
}
