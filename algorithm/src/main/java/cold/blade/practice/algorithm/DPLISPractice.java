package cold.blade.practice.algorithm;

/**
 * @Description 利用动态规划算法解决最长子序列的问题
 *
 * @author cold_blade
 * @date 2020/10/29
 * @version 1.0
 */
public final class DPLISPractice {

    private DPLISPractice() {
    }

    public static int length(int[] array) {
        if (null == array || array.length == 0) {
            return 0;
        }
        // 数组0索引处的最长递增子序列的长度为1（已知条件）
        int result = 1;
        for (int index = 1; index < array.length; index++) {
            // 以index为子序列的结束位置
            int maxLen = -1;
            int startIndex = 0;
            do {
                int len = 1;
                int lastIndex = startIndex;
                for (int i = startIndex; i < index; i++) {
                    if (array[i] < array[index] && (startIndex == lastIndex || array[i] > array[lastIndex])) {
                        len++;
                        lastIndex = i;
                    }
                }
                maxLen = Math.max(len, maxLen);
                startIndex++;
            } while (startIndex < index);

            result = Math.max(result, maxLen);
        }

        return result;
    }
}