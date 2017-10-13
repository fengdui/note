/**
 * Created by fd on 2016/5/13.
 * 旋转数组的最小数字
 */
public class Pro_6 {

    public int minNumberInRotateArray(int [] array) {
        if (array.length == 0) {
            return 0;
        }
//        //复杂度o(n)
//        int n = array.length;
//        for (int i = 1; i < n; i++) {
//            if (array[i] < array[i-1]) {
//                return array[i];
//            }
//        }
//        return array[0];
        int l = 0, r = array.length-1;
        int ans = 0;
        while (l < r) {
            int mid = (l+r) >> 1;
            if (array[mid] < array[l]) {
                r = mid;
                ans = array[mid];
            }
            else if (array[mid] > array[l]) {
                l = mid=1;
            }
        }
        return 0;
    }
}
