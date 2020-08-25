package cold.blade.algorithm.sort;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @Description
 *      计数排序
 *      思路：该排序算法的应用场景非常有局限性，首先，它要求数组中的数值必须为自然数，其次，它非常的浪费空间，
 *           主要是辅助数组是由待排序数组中的最大值决定的。
 *
 * @author cold_blade
 * @date 2020/8/25
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CountSort {

    public static void sort(int[] intArray) {
        int maxVal = getMaxValue(intArray);
        int[] buckets = new int[maxVal + 1];
        for (int intVal : intArray) {
            buckets[intVal]++;
        }
        int index = 0;
        for (int i = 0; i < buckets.length; i++) {
            while (buckets[i] > 0) {
                intArray[index++] = i;
                buckets[i]--;
            }
        }
    }

    private static int getMaxValue(int[] array) {
        int maxVal = array[0];
        for (int i = 1; i < array.length; i++) {
            maxVal = Math.max(maxVal, array[i]);
        }
        return maxVal;
    }
}
