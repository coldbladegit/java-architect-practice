package cold.blade.algorithm.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @Description 数组操作的工具包
 *
 * @author cold_blade
 * @date 2020/8/18
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArrayUtil {

    /**
     * 数组两个值的交换
     *
     * @param array 数组
     * @param srcIndex 源索引
     * @param tgtIndex 目标索引
     */
    public static void swap(int[] array, int srcIndex, int tgtIndex) {
        int tmp = array[tgtIndex];
        array[tgtIndex] = array[srcIndex];
        array[srcIndex] = tmp;
    }
}
