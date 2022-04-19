package cold.blade.practice.algorithm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * @Description 利用动态规划算法解决最长子序列的问题
 *
 * @author cold_blade
 * @date 2020/10/29
 * @version 1.0
 */
public final class DPLISPractice {

    private volatile Long counter = 1L;

    private DPLISPractice() {
    }

    public static int length(int[] array) {
        if (null == array || array.length == 0) {
            return 0;
        }
        // 数组0索引处的最长递增子序列的长度为1（已知条件）
        int result = 1;
        for (int index = 1; index < array.length; index++) {
            // 以index为子序列的结束位置
            int maxLen = -1;
            int startIndex = 0;
            do {
                int len = 1;
                int lastIndex = startIndex;
                for (int i = startIndex; i < index; i++) {
                    if (array[i] < array[index] && (startIndex == lastIndex || array[i] > array[lastIndex])) {
                        len++;
                        lastIndex = i;
                    }
                }
                maxLen = Math.max(len, maxLen);
                startIndex++;
            } while (startIndex < index);

            result = Math.max(result, maxLen);
        }

        return result;
    }

    public static void main(String[] args) {
        List<TestClass> list = new ArrayList<>();
        list.add(new TestClass(1, 1));
        list.add(new TestClass(2, 3));
        list.stream()
            .map(TestClass::getAge)
            .forEach(System.out::println);
        System.out.println(String.format("cold_blade(%s : %s)", "test", null));

        LocalDate day = LocalDate.now().minusDays(3);
        System.out.println(Collections.emptyList().get(0));
    }

    private static class TestClass {

        private int age;
        private int no;

        TestClass(int age, int no) {
            this.age = age;
            this.no = no;
        }

        public int getAge() {
            if (age == 3) {
                throw new RuntimeException("test");
            }
            return age;
        }

        public int getNo() {
            return no;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }
}