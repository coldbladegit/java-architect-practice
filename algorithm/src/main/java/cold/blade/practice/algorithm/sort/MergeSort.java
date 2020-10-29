package cold.blade.practice.algorithm.sort;

import java.util.Deque;
import java.util.LinkedList;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @Description 归并排序算法
 *   思路：归并的核心思想就是先将问题拆成一个个易于解决的小的子问题，然后再将各个子问题的
 *        结果做合并，最终构成该问题的最终结果
 *
 * @author cold_blade
 * @date 2020/8/17
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MergeSort {

    public static void sort(int[] array) {
        Deque<ArrayIndexer> indexers = divide(array);
        ArrayIndexer indexer;
        ArrayIndexer anotherIndexer;
        while (indexers.size() > 1) {
            indexer = indexers.removeFirst();
            anotherIndexer = indexers.removeFirst();
            if (indexer.start > anotherIndexer.start) {
                // 确保所有的两个数组合并都是合并相邻的两个数组（在原数组中），通常数组的长度为奇数时会出现这种场景
                indexers.addLast(indexer);
                indexers.addFirst(anotherIndexer);
            } else {
                indexers.addLast(merge(array, indexer, anotherIndexer));
            }
        }
    }

    private static ArrayIndexer merge(int[] array, ArrayIndexer indexer, ArrayIndexer anotherIndexer) {
        int key;
        int index;
        for (int j = anotherIndexer.start; j <= anotherIndexer.end; j++) {
            key = array[j];
            index = -1;
            for (int i = indexer.end; i >= indexer.start; i--) {
                if (key < array[i]) {
                    index = i;
                }
            }
            if (index != -1) {
                // 从索引i到j处的元素都后移一位
                moveRight(array, index, j);
                array[index] = key;
            }
        }
        // 构造新的一个数组索引
        indexer.end = anotherIndexer.end;

        return indexer;
    }

    private static void moveRight(int[] array, int start, int end) {
        do {
            array[end] = array[--end];
        } while (start < end);
    }

    private static Deque<ArrayIndexer> divide(int[] array) {
        LinkedList<ArrayIndexer> indexers = new LinkedList<>();
        for (int i = 0; i < array.length; i++) {
            indexers.addLast(new ArrayIndexer(i, i));
        }

        return indexers;
    }

    @AllArgsConstructor
    private static class ArrayIndexer {

        private int start;
        private int end;
    }
}
