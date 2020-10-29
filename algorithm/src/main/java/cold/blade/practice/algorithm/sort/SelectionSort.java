package cold.blade.practice.algorithm.sort;

import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * @Description 选择排序
 *   思路：假设数组长度为n,每一次遍历数组并选择数组中的最大/小，将选取出来的值置于遍历的数组范围的
 *        首位，进行n-1次这样的遍历后就得到了一个有序的数组
 *
 * @author cold_blade
 * @date 2020/7/6
 * @version 1.0
 */
public class SelectionSort<T extends Comparable<? super T>> {

    /**
     * 从小到大进行的自然排序
     *
     * @param array 待排序数组
     */
    public void sort(T[] array) {
        sort(array, (t1, t2) -> t1.compareTo(t2) > 0);
    }

    /**
     * 从大到小进行的反向自然排序
     *
     * @param array 待排序数组
     */
    public void reverse(T[] array) {
        sort(array, (t1, t2) -> t1.compareTo(t2) < 0);
    }

    private void sort(T[] array, BiPredicate<T, T> compare) {
        if (Objects.isNull(array)) {
            return;
        }
        int len = array.length;
        int keyIndex;
        T tmp;
        for (int i = 0; i < len; ++i) {
            keyIndex = i;
            for (int j = i + 1; j < len; ++j) {
                if (compare.test(array[keyIndex], array[j])) {
                    keyIndex = j;
                }
            }
            // 一次遍历，将最小/大值放置于数组的遍历初始位置
            if (keyIndex != i) {
                tmp = array[i];
                array[i] = array[keyIndex];
                array[keyIndex] = tmp;
            }
        }
    }
}
