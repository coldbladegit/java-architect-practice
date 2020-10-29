package cold.blade.practice.algorithm.sort;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

import lombok.AllArgsConstructor;

/**
 * @Description 快速排序
 *   思路：从数组中选取一个基准（通常选取数组第一个位置），并设置两个哨兵（i, j）分列数组首尾两端。
 *   1、先从j开始向左移动(j--)，直到遇到比基准数小的时候暂停；
 *   2、接着从i开始向右移动(i++)，直到遇到比基准数大的时候暂停；
 *   3、交换此时i,j两个哨兵处的值，然后重复步骤1、2，直到 i == j，基准与 i或j 进行交换；
 *   4、以 i或j 为界，将数组动态划分为两个子数组，重复以上步骤1、2、3，直至不能再划分子数组为止；
 *
 * @author cold_blade
 * @date 2020/7/7
 * @version 1.0
 */
public class QuickSort<T extends Comparable<? super T>> {

    public void sort(T[] array) {
        if (Objects.isNull(array) || array.length == 1) {
            return;
        }

        int key;
        Deque<SortParam> params = new ArrayDeque<>();
        params.push(new SortParam(0, array.length - 1));
        SortParam param;
        do {
            param = params.pop();
            key = doSort(array, param.start, param.end);
            if (key - 1 > param.start) {
                params.push(new SortParam(param.start, key - 1));
            }
            if (key + 1 < param.end) {
                params.push(new SortParam(key + 1, param.end));
            }
        } while (!params.isEmpty());
    }

    private int doSort(T[] array, int start, int end) {
        // 选取子数组的首位做为基准位
        int keyIndex = start;
        T tmp;
        do {
            while (end > start && array[keyIndex].compareTo(array[end]) <= 0) {
                end--;
            }
            while (end > start && array[keyIndex].compareTo(array[start]) >= 0) {
                start++;
            }
            if (end > start) {
                tmp = array[start];
                array[start] = array[end];
                array[end] = tmp;
            }
        } while (start < end);
        // 将基准位的值放置在数组中正确位置
        if (keyIndex != start) {
            tmp = array[keyIndex];
            array[keyIndex] = array[start];
            array[start] = tmp;
        }
        return start;
    }

    @AllArgsConstructor
    private static class SortParam {

        private int start;
        private int end;
    }
}
