/**
 * Pro_37
 * 数字在排序数组中出现的次数
 * @author FD
 * @date 2016/5/17
 */
public class Pro_37 {
    public int GetNumberOfK(int [] array , int k) {
        if (array.length == 0) {
            return 0;
        }
        int start = 0, end = array.length-1;
        int l = 0, r = array.length-1;
        while (l <= r) {
            int mid = (l+r) >> 1;
            if (array[mid] < k) {
                start = mid+1;
                l = mid+1;
            }
            else {
                r = mid-1;
            }
        }
        l = 0;
        r = array.length-1;
        while (l <= r) {
            int mid = (l+r) >> 1;
            if (array[mid] > k) {
                end = mid-1;
                r = mid-1;
            }
            else {
                l = mid+1;
            }
        }
        if (start < array.length && array[start] != k) {
            return 0;
        }
        return end-start+1;
    }

    public static void main(String[] args) {
        int[] a = {2, 2, 2, 2, 3, 4};
        System.out.println(new Pro_37().GetNumberOfK(a, 2));
    }
}
