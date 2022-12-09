package cold.blade.technology;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cold_blade
 * @date 2022/9/26
 * @version 1.0
 */
@Slf4j
public class CommonTest extends BaseTest {

    @Test
    public void test() {
        DateTime now = DateTime.now().minusMonths(8).minusDays(25);
        DateTime dateTime = new DateTime(System.currentTimeMillis(), DateTimeZone.getDefault());
        log.info("===[cold_blade]=== [{}]", now.getWeekyear() + "-" + now.getWeekOfWeekyear());
        log.info("===[cold_blade]=== [{}]", dateTime.toString("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    public void test1() {
        LocalTime now = LocalTime.now();
        log.info("===[cold_blade]=== [{}]", now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        LocalTime time = LocalTime.parse("15:50:00", DateTimeFormatter.ofPattern("HH:mm:ss"));
        log.info("===[cold_blade]=== [{}]", time.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    @Test
    public void test2() {
        long timestamp = System.currentTimeMillis();
        LocalDateTime dateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
        String formatStr = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        log.info("===[cold_blade]=== [{}]", formatStr);
    }

    @Test
    public void test3() {
        String jsonStr = "[1, 2]";
        List<Integer> integers = JSON.parseArray(jsonStr, Integer.class);
        log.info("===[cold_blade]=== {}", integers);
    }
}
