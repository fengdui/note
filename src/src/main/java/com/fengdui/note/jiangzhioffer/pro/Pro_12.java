import java.util.Map;

/**
 * Pro_12
 * 数值的整数次方
 * @author FD
 * @date 2016/5/13
 */
public class Pro_12 {
    public double Power(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        }
        boolean flag = false;
        if (exponent < 0) {
            flag = true;
            exponent = Math.abs(exponent);
        }
        double b;
        if ((exponent&1) == 1 && base < 0) {
            b = -1;
        }
        else {
            b = 1;
        }
        base = Math.abs(base);
        double ans = 1;
        while (exponent > 0) {
            if ((exponent&1) == 1) {
                ans *= base;
            }
            base *= base;
            exponent >>= 1;
        }
        ans *= b;
        if (flag) {
            ans = 1/ans;
        }
        return ans;
    }
}
