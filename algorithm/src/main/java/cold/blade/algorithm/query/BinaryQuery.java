package cold.blade.algorithm.query;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @Description 二分查找
 *
 * @author cold_blade
 * @date 2020/9/24
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BinaryQuery {

    public static int query(int[] array, int target) {
        if (array.length <= 0) {
            return -1;
        }
        int begin = 0;
        int end = array.length - 1;
        int mid;
        do {
            mid = (begin + end) / 2;
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {
                begin = mid + 1;
            } else {
                end = mid - 1;
            }
        } while (begin <= end);
        return -1;
    }
}
