package cold.blade.technology.service.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import cold.blade.technology.service.TransmittableThreadLocalService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cold_blade
 * @date 2022/3/1
 * @version 1.0
 */
@Slf4j
@Service
public class TransmittableThreadLocalServiceImpl implements TransmittableThreadLocalService {

    private final TransmittableThreadLocal<String> traceIdThreadLocal = new TransmittableThreadLocal<>();

    private ExecutorService executorService = TtlExecutors
        .getTtlExecutorService(new ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10),
            new ThreadFactoryBuilder().setNameFormat("transmittable_%s").setDaemon(true).build(), new ThreadPoolExecutor.CallerRunsPolicy()));

    @Override
    public void execute(String value) {
        traceIdThreadLocal.set(value);
        for (int index = 0; index < 2; index++) {
            executorService.submit(new SubTask(index));
        }

        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
            String traceId = traceIdThreadLocal.get();
            log.info("executor is terminated. traceId:{}", traceId);
        } catch (InterruptedException e) {
            log.error("broke out some exception.", e);
        }
    }

    private class SubTask implements Callable {

        private String threadNamePrefix;

        private SubTask(int index) {
            this.threadNamePrefix = "SubTask_" + index;
        }

        @Override
        public Object call() {
            String traceId = traceIdThreadLocal.get();
            log.info("{} ..... {}", threadNamePrefix, traceId);
            return traceId;
        }
    }
}
