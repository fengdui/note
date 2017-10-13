/**
 * Created by fd on 2016/5/13.
 * 斐波那契数列
 */
public class Pro_7 {
    public int Fibonacci(int n) {
        if(n == 0){
            return 0;
        }else if(n == 1){
            return 1;
        }

        int one = 1;
        int two = 0;
        int sum = 0;
        for (int i = 2; i <= n; i++) {
            sum = one + two;
            two = one;
            one = sum;
        }
        return sum;
    }
}
