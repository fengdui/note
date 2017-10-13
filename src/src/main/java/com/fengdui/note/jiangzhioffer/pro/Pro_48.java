/**
 * Pro_48
 * 不用加减乘除做加法
 * @author FD
 * @date 2016/5/18
 */
public class Pro_48 {
    public int Add(int num1,int num2) {
//        int ans = 0, p = 0;
//        for (int i = 0; i < 31; i++) {
//            if(((1<<i)&num1) > 0 && ((1<<i)&num2) > 0) {
//                if (p > 0) {
//                    ans ^= 1<<i;
//                }
//                else {
//
//                }
//                p = 1<<(i+1);
//            }
//            else if (((1<<i)&num1) > 0 || ((1<<i)&num2) > 0) {
//                if (p > 0) {
//                    p = 1<<(i+1);
//                }
//                else {
//                    ans ^= 1<<i;
//                }
//            }
//            else {
//                if (p > 0) {
//                    ans ^= 1<<i;
//                }
//                p = 0;
//            }
//        }
//        return ans;
        while (num2 != 0) {
            int temp = num1^num2;
            num2 = (num1&num2)<<1;
            num1 = temp;
        }
        return num1;
    }
}
