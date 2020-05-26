package cold.blade.technology.service;

import java.util.concurrent.TimeUnit;

/**
 * @author cold_blade
 * @version 1.0
 */
public interface ScheduleService {

    void scheduleWithFixDelay(long initialDelay, long delay, TimeUnit unit);
}
