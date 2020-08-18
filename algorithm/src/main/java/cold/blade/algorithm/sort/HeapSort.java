package cold.blade.algorithm.sort;

import cold.blade.algorithm.util.ArrayUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @Description 堆排序的实现
 *   思路：堆排序的基本思想是：将待排序序列构造成一个大顶堆，此时，整个序列的最大值就是堆顶的根节点。
 *        将其与末尾元素进行交换，此时末尾就为最大值。然后将剩余n-1个元素重新构造成一个堆，这样会得到n个元素的次小值。
 *        如此反复执行，便能得到一个有序序列了
 *
 * @author cold_blade
 * @date 2020/8/18
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HeapSort {

    public static void sort(int[] array) {
        int rootNodeValue;
        for (int i = array.length - 1; i > 0; i--) {
            // 构造大根堆
            buildBigHeap(array, i);
            // 交换大根堆的根节点与数组末尾的元素
            rootNodeValue = array[0];
            array[0] = array[i];
            array[i] = rootNodeValue;
        }
    }

    private static void buildBigHeap(int[] array, int end) {
        int parentIndex;
        do {
            if (end % 2 == 0) {
                // 叶节点的索引坐标为偶数，则根据完全二叉树的特性，该父节点一定有两个子节点
                parentIndex = (end - 2) / 2;
                if (array[parentIndex] < array[end]) {
                    ArrayUtil.swap(array, parentIndex, end);
                }
                if (array[parentIndex] < array[end - 1]) {
                    ArrayUtil.swap(array, parentIndex, end - 1);
                }
            } else {
                parentIndex = (end - 1) / 2;
                if (array[parentIndex] < array[end]) {
                    ArrayUtil.swap(array, parentIndex, end);
                }
            }
            end = parentIndex;
        } while (end > 0);
    }


}
