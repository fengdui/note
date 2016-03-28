package net.smgui.common;

import java.util.Scanner;

/**
 * Main2
 *
 * @author FD
 * @date 2016/3/24 0024
 */
public class Main2 {
    public static long getSum(long res) {

        long ans = 0;
        while (res > 0) {
            ans += res%10;
            res /= 10;
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long a, b, n;
        a = sc.nextLong();
        b = sc.nextLong();
        n = sc.nextLong();
        long ans1 = 0, ans2 = -1;
        for (long i = a; i <= b; i++) {
            if (getSum(i) == n) {
                ans1++;
                if (ans2 < 0)
                ans2 = i;
            }
        }
        System.out.println(ans1);
        System.out.println(ans2);
    }
}
