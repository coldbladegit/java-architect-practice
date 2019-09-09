package com.cold.blade.architect.stream;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

/**
 * @version 1.0
 */
public class WordCounterSpliter implements Spliterator<Character> {

    private final String string;
    private final IntPredicate minSizePredicate;
    private int curIndex = 0;

    public WordCounterSpliter(String string, IntPredicate minSizePredicate) {
        this.string = string;
        this.minSizePredicate = minSizePredicate;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(string.charAt(curIndex++));
        return curIndex < string.length();
    }

    @Override
    public Spliterator<Character> trySplit() {
        int remainedSize = string.length() - curIndex;
        if (!minSizePredicate.test(remainedSize)) {
            return null;
        }
        int splitIndex = curIndex + remainedSize / 2;
        for (; splitIndex < string.length(); splitIndex++) {
            if (Character.isWhitespace(string.charAt(splitIndex))) {
                WordCounterSpliter spliter = new WordCounterSpliter(string.substring(curIndex, splitIndex), minSizePredicate);
                curIndex = splitIndex;
                return spliter;
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return string.length() - curIndex;
    }

    @Override
    public int characteristics() {
        return ORDERED | SIZED | SUBSIZED | NONNULL | IMMUTABLE;
    }
}
