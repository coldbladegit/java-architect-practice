package com.cold.blade.rx;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;

/**
 * @author cold_blade
 * @date 2022/7/19
 * @version 1.0
 */
@Slf4j
@Component
public class RxDemo {

    public void doExecute() {
        Observer<String> onNextObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                log.info("rx demo execute completed...");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                log.info("rx demo observer on next: [" + s + "]...");
            }
        };

        Observable<String> observable = Observable.create(subscriber -> {
            subscriber.onNext("Hello world");
            subscriber.onCompleted();
        });
        log.info("rx demo start...");
        observable.subscribe(onNextObserver);
        log.info("rx demo end...");
    }
}
