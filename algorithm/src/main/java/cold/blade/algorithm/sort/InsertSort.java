package cold.blade.algorithm.sort;

import java.util.Objects;

/**
 * @Description 插入排序算法
 *   思路：插入排序运用了递归的思想，针对当前索引i，其[0, i - 1]的子数组是已排好序的。
 *
 * @author cold_blade
 * @date 2020/7/7
 * @version 1.0
 */
public class InsertSort<T extends Comparable<? super T>> {

    public void sort(T[] array) {
        if (Objects.isNull(array) || array.length == 1) {
            return;
        }
        T tmp;
        int index;
        for (int i = 1; i < array.length; ++i) {
            index = i;
            for (int j = i - 1; j >= 0; --j) {
                if (array[i].compareTo(array[j]) < 0) {
                    index--;
                }
            }
            if (index != i) {
                tmp = array[i];
                // 通过系统内部的数组copy实现元素的移动
                System.arraycopy(array, index, array, index + 1, i - index);
                array[index] = tmp;
            }
        }
    }
}
