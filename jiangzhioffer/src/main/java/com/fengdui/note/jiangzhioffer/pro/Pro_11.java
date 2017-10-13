/**
 * Created by fd on 2016/5/13.
 * 二进制中1的个数
 */
public class Pro_11 {
    public int  NumberOf1(int n) {
        int ans = 0;
        while (n != 0) {
            ans++;
            n &= (n-1);
        }
        return ans;
    }
}
