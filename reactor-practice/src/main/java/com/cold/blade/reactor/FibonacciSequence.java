package com.cold.blade.reactor;

import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;

import lombok.AllArgsConstructor;

/**
 * 斐波那契数列
 *
 * @version 1.0
 */
public final class FibonacciSequence {

    private FibonacciSequence() {
    }

    public static int elementAt(int index) {
        Preconditions.checkArgument(index >= 0);
        return iterate(index + 1)[index];
    }

    public static int[] iterate(int size) {
        Preconditions.checkArgument(size > 0);
        return Stream.iterate(new FibonacciSequenceState(0, 1), e -> {
                int next = e.previous + e.current;
                e.previous = e.current;
                e.current = next;
                return e;
            }
        ).mapToInt(e -> e.previous)
            .limit(size)
            .toArray();
    }

    public static int[] generate(int size) {
        Preconditions.checkArgument(size > 0);

        return IntStream.generate(new IntSupplier() {
            private int previous = 0;
            private int current = 1;

            @Override
            public int getAsInt() {
                int ret = previous;
                int next = previous + current;
                previous = current;
                current = next;
                return ret;
            }
        }).limit(size).toArray();
    }

    @AllArgsConstructor
    private static class FibonacciSequenceState {

        private int previous;
        private int current;
    }
}
