package cold.blade.algorithm.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @Description 桶排序
 *      思路：
 *          桶排序的关键在于如何根据待排序数组设置合理的桶(也看做一个数组)的数量， 
 *          两个值之间需要寻找一个合理的映射函数，这样才能保证该算法的高效。
 *          如果取到合理的桶值，根据映射函数，我们可以将待排序数组均匀的分到每个桶中，
 *          再分别对各个桶中的元素进行排序，最后我们直接输出桶中的数据就好。
 *
 * @author cold_blade
 * @date 2020/8/31
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BucketSort {

    public static void sort(int[] array) {
        if (array.length <= 0) {
            return;
        }
        int maxVal = array[0];
        int minVal = array[0];
        // 找出数组最大/小值
        for (int item : array) {
            maxVal = Math.max(maxVal, item);
            minVal = Math.min(minVal, item);
        }
        // 计算桶的个数
        int bucketSize = (maxVal - minVal) / array.length + 1;
        // 初始化桶
        List<List<Integer>> buckets = new ArrayList<>(bucketSize);
        for (int i = 0; i < bucketSize; i++) {
            buckets.add(new ArrayList<>());
        }
        for (int item : array) {
            // 映射到具体的桶索引
            int index = (item - minVal) / array.length;
            buckets.get(index).add(item);
        }
        // 每个桶内部利用JDK的TimSort排序算法
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
        }
        int index = 0;
        for (int i = 0; i < bucketSize; i++) {
            List<Integer> bucket = buckets.get(i);
            for (int j = 0; j < bucket.size(); j++) {
                array[index++] = bucket.get(j);
            }
        }
    }
}
