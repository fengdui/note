import java.util.Arrays;

/**
 * Pro_13
 * 调整数组顺序使奇数位于偶数前面
 * @author FD
 * @date 2016/5/13
 */
public class Pro_13 {
    public void reOrderArray(int [] array) {
        int[] tmp = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            tmp[i] = array[i];
        }
        int pos = 0;
        for (int i = 0; i < array.length; i++) {
            if ((tmp[i]&1) == 1) {
                array[pos++] = tmp[i];
            }
        }
        for (int i = 0; i < array.length; i++) {
            if ((tmp[i]&1) == 0) {
                array[pos++] = tmp[i];
            }
        }
    }
}
