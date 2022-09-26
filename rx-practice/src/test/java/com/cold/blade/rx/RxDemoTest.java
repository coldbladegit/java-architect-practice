package com.cold.blade.rx;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import lombok.Getter;
import lombok.Setter;

/**
 * @author cold_blade
 * @date 2022/7/19
 * @version 1.0
 */
public class RxDemoTest extends BaseTest {

    @Autowired
    private RxDemo rxDemo;

    @Test
    public void test() {
        rxDemo.doExecute();
        try {
            LocalDateTime now = LocalDateTime.now();
            long diff = localDateTimeToMillis(LocalDate.now().atTime(now.toLocalTime())) - localDateTimeToMillis(now);
            System.out.println("===[LOCAL_DATA_TIME]=== result = " + diff);
            System.out.println("===[LOCAL_DATA_TIME]=== result = " + now.isBefore(now));
            System.out.println("===[LOCAL_DATA_TIME]=== result = " + now.isAfter(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() {
        JsonClass clazz = new JsonClass();
        clazz.setName("cold_blade");
        clazz.setSex("M");
        clazz.setHighs(Lists.newArrayList(1, 2, 3));
        clazz.setHighs(Collections.emptyList());
        JsonClass cloneObj = new JsonClass();
        BeanUtils.copyProperties(clazz, cloneObj);
        System.out.println(cloneObj);
    }

    public static long localDateTimeToMillis(LocalDateTime localDateTime) {

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return zdt.toInstant().toEpochMilli();
    }


    @Setter
    @Getter
    private static class JsonClass {

        @JSONField(name = "姓名", ordinal = 1)
        private String name;
        @JSONField(name = "性别", ordinal = 3)
        private String sex;
        @JSONField(name = "身高", serializeUsing = HighSerializer.class, ordinal = 2)
        private List<Integer> highs;

        @Override
        public String toString() {
            String str = JSON.toJSONString(this);
            str = str.replace("{", "");
            str = str.replace("}", "");
            str = str.replace(":", " : ");
            str = str.replace(",", "\n");
            str = str.replace("\"", "");
            return str;
        }
    }

    public static class HighSerializer implements ObjectSerializer {

        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
            if (object instanceof List) {
                List list = (List) object;
                if (list.size() > 0) {
                    String mergeText = list.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("、"))
                        .toString();
                    serializer.write(mergeText);
                }
            }
        }
    }
}
