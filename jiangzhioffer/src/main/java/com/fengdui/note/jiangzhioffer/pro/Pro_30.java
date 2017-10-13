/**
 * Pro_30
 * 连续子数组的最大和
 * @author FD
 * @date 2016/5/16
 */
public class Pro_30 {
    public int FindGreatestSumOfSubArray(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int ans = array[0];
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            ans = Math.max(sum, ans);
            if (sum < 0) {
                sum = 0;
            }
        }
        return ans;
    }
}
