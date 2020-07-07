package cold.blade.algorithm.sort;

import java.util.Objects;

/**
 * @Description 冒泡排序
 *   思路：相邻元素前后交换、把最大的排到最后。
 *   技巧：当一次遍历都未发生过元素位置交换，则说明从当次开始遍历的索引起始直至数组最后的子数组是已排好序的
 *
 * @author cold_blade
 * @date 2020/7/6
 * @version 1.0
 */
public class BubbleSort<T extends Comparable<? super T>> {

    public void sort(T[] array) {
        if (Objects.isNull(array) || array.length == 1) {
            return;
        }

        T tmp;
        boolean swapped = true;
        for (int i = 0; i < array.length && swapped; ++i) {
            swapped = false;
            for (int j = 0; j < array.length - 1 - i; ++j) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    swapped = true;
                }
            }
        }
    }
}
