package com.cold.blade.architect.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public final class WordCounter {

    private final int count;
    private final boolean lastSpace;

    public WordCounter accumulate(Character ch) {
        if (Character.isWhitespace(ch)) {
            return lastSpace ? this : new WordCounter(this.count, true);
        } else {
            return lastSpace ? new WordCounter(this.count + 1, false) : this;
        }
    }

    public WordCounter combine(WordCounter counter) {
        return new WordCounter(this.count + counter.count, counter.lastSpace);
    }
}
