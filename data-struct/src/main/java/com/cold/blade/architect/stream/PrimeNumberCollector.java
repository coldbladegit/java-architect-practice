package com.cold.blade.architect.stream;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @version 1.0
 */
@Component
public class PrimeNumberCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> {
            Map<Boolean, List<Integer>> numbers = Maps.newHashMap();
            numbers.put(true, Lists.newArrayList());
            numbers.put(false, Lists.newArrayList());
            return numbers;
        };
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (numbers, number) -> numbers.get(isPrime(numbers.get(true), number)).add(number);
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (partNumbers1, partNumbers2) -> {
            partNumbers1.get(true).addAll(partNumbers2.get(true));
            partNumbers1.get(false).addAll(partNumbers2.get(false));
            return partNumbers1;
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }

    private boolean isPrime(List<Integer> primeNumbers, int candidate) {
        final int sqrt = (int) Math.sqrt(candidate);
        return takeWhile(primeNumbers, number -> number <= sqrt)
            .stream().noneMatch(i -> candidate % i == 0);
    }

    private List<Integer> takeWhile(List<Integer> numbers, Predicate<Integer> condition) {
        int index = 0;
        for (Integer number : numbers) {
            if (!condition.test(number)) {
                return numbers.subList(0, index);
            }
            index++;
        }
        return numbers;
    }
}
