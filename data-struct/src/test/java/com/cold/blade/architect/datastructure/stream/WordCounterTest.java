package com.cold.blade.architect.datastructure.stream;

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import org.junit.Assert;
import org.junit.Test;

import com.cold.blade.architect.BaseTest;
import com.cold.blade.architect.stream.WordCounter;
import com.cold.blade.architect.stream.WordCounterSpliter;

/**
 * @version 1.0
 */
public class WordCounterTest extends BaseTest {

    private final String SENTENCE = "  Nel mezzo del cammin di nostra vita mi ritrovai in una selva oscura" +
        " ché la dritta via era smarrita ";

    @Test
    public void testSequenceCount() {
        WordCounter counter = IntStream.range(0, SENTENCE.length())
            .mapToObj(SENTENCE::charAt)
            .reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        Assert.assertEquals(19, counter.getCount());
    }

    @Test
    public void testParallelCount() {
        WordCounterSpliter spliter = new WordCounterSpliter(SENTENCE, size -> size >= 10);
        WordCounter counter = StreamSupport.stream(spliter, true)
            .reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        Assert.assertEquals(19, counter.getCount());
    }
}
