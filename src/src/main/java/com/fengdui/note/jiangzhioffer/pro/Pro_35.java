/**
 * Pro_35
 * 数组中的逆序对
 * @author FD
 * @date 2016/5/17
 */
public class Pro_35 {
    public int InversePairs(int [] array) {
        int sum = sort(array, 0, array.length-1);
        return sum;
    }
    public int sort(int[] array, int start, int end) {
        if (start >= end) {
            return 0;
        }
        int mid = (start + end) / 2;
        int ans = sort(array, start, mid) + sort(array, mid + 1, end);
        int len1 = start, len2 = mid + 1;
        int[] temp = new int[end - start + 1];
        int len = 0;
        while (len2 <= end) {
            while (len1 <= mid && array[len1] <= array[len2]) {
                temp[len++] = array[len1];
                len1++;
            }
            if (len1 <= mid)
                ans += mid - len1 + 1;
            temp[len++] = array[len2];
            len2++;
        }
        while (len1 <= mid) {
            temp[len++] = array[len1];
            len1++;
        }
        for (int i = start, j = 0; i <= end; i++) {
            array[i] = temp[j++];
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] a = {1,2,1,2,1};
        new Pro_35().InversePairs(a);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]+" ");
        }
    }
}
