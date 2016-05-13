/**
 * Created by fd on 2016/5/13.
 * 矩形覆盖
 */
public class Pro_10 {
    public int RectCover(int target) {
        if (target == 1) {
            return 1;
        }else if (target == 2) {
            return 2;
        }
        int a = 1, b = 2, sum = 0;
        for (int i = 3; i <= target; i++) {
            sum = a+b;
            a = b;
            b = sum;
        }
        return sum;
    }
}
