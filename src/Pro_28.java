/**
 * Pro_28
 * 数组中出现次数超过一半的数字
 * @author FD
 * @date 2016/5/16
 */
public class Pro_28 {
    public int MoreThanHalfNum_Solution(int [] array) {
        if (array.length == 0) {
            return 0;
        }
        int sum = 1;
        int x = array[0];
        for (int i = 1; i < array.length; i++) {
            if (sum == 0) {
                sum = 1;
                x = array[i];
            }
            else if (array[i] == x) {
                sum++;
            }
            else {
                sum--;
            }
        }
        sum = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == x) {
                sum++;
            }
        }
        if (sum >= (array.length+2)/2) {
            return x;
        }
        return 0;
    }
}
