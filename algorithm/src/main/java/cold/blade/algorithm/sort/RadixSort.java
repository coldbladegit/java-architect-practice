package cold.blade.algorithm.sort;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @Description 基数排序算法，这里仅对十进制的数做排序
 *      核心思想：从个位一直到数组中最大数的位数依次比较并排序
 *
 * @author cold_blade
 * @date 2020/9/11
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RadixSort {

    public static void sort(int[] array) {
        // 基数，这里以十进制举例
        int[] temArray = new int[array.length];
        int max = max(array);
        int exp = 1;
        int[] sortArray = array;
        do {
            doSort(sortArray, temArray, exp);
            // 相互交换两个数组的值
            array = sortArray;
            sortArray = temArray;
            temArray = array;
            exp *= 10;
        } while (max / exp > 0);
        // 将排好序的数组copy回原数组
        for (int i = 0, len = array.length; i < len; i++) {
            array[i] = sortArray[i];
        }
    }

    private static int max(int[] array) {
        int maxVal = array[0];
        for (int i = 1, len = array.length; i < len; i++) {
            maxVal = Math.max(maxVal, array[i]);
        }
        return maxVal;
    }

    private static void doSort(int[] array, int[] temArray, int exp) {
        int[] counter = new int[10];
        // 统计将数组中的数字分配到桶中后，各个桶中的数字个数
        for (int num : array) {
            counter[num / exp % 10]++;
        }
        // 将counter中的值从各桶中的个数转化为各桶中最后一个数在辅助数组中的索引（需要减去1 数组索引从0开始）
        for (int i = 1; i < 10; i++) {
            counter[i] += counter[i - 1];
        }
        // 由于counter中的值是各桶中最后一个数的索引，故需要反序遍历原始数组
        for (int i = array.length - 1; i >= 0; i--) {
            int index = array[i] / exp % 10;
            temArray[counter[index] - 1] = array[i];
            counter[index]--;
        }
    }
}
