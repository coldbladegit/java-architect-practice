package com.cold.blade.architect.design_patterns;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import lombok.Getter;

/**
 * @version 1.0
 */
public final class ObserverMode {

    public static class NewsSubject {

        private final List<Consumer<String>> newsConsumers = Lists.newArrayList();

        public void register(Consumer<String> newsConsumer) {
            newsConsumers.add(newsConsumer);
        }

        public void notification(String tweet) {
            newsConsumers.forEach(consumer -> consumer.accept(tweet));
        }
    }

    @Getter
    public static class NewYorkTimes {

        private boolean dealt;

        public void dealNewsNotification(String tweet) {
            dealt = tweet.contains("money");
        }
    }

    @Getter
    public static class Guardian {

        private boolean dealt;

        public void dealNewsNotification(String tweet) {
            dealt = tweet.contains("queen");
        }
    }
}
