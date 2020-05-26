package cold.blade.technology.service.impl;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import cold.blade.technology.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cold_blade
 * @version 1.0
 */
@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService, InitializingBean {

    private static final int SCAN_INIT_DELAY_SECONDS = 30;
    private static final int SCAN_DELAY_SECONDS = 3;

    private Random random = new Random();
    private ScheduledExecutorService scanExecutor = new ScheduledThreadPoolExecutor(5,
        new ThreadFactoryBuilder().setNameFormat("delay_proxy_event_scan_%s").setDaemon(true).build(), new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public void scheduleWithFixDelay(long initialDelay, long delay, TimeUnit unit) {

    }

    @Override
    public void afterPropertiesSet() {
        scanExecutor.scheduleWithFixedDelay(this::process, SCAN_INIT_DELAY_SECONDS, SCAN_DELAY_SECONDS, TimeUnit.SECONDS);
    }

    private void process() {
        log.info("====== time:{}", LocalDateTime.now());
        int cost = SCAN_DELAY_SECONDS + random.nextInt(10);
        log.info("====== cost:{}", cost);
        try {
            TimeUnit.SECONDS.sleep(cost);
        } catch (InterruptedException e) {
        }
    }
}
