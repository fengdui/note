package com.dengdui.test.editor.common;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
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
        float[] f = {-6, -8, 10, -8};
        int[] ans = Program.Puzzle(f);
        System.out.println(Arrays.toString(ans));
    }

    public static class Program {
        public static int[] Puzzle(float[] a) {
            int n = a.length;
            float[] b = new float[n];
            for(int i = 0; i < n; i++) {
                b[i] = a[i];
            }
            int[] c = new int[n];
            HashMap<Float, Integer> map = new HashMap<Float, Integer>();
            Arrays.sort(a);
            int cnt = 0;
            for(int i = 0; i < n; ) {
                map.put(a[i], cnt);
                int j = i+1;
                while (j < n && a[i] == a[j]) {
                    j++;
                    cnt++;
                }
                cnt++;
                i = j;
            }
            for (int i = 0; i < n;i++) {
                c[i] = map.get(b[i]);
            }
            return c;
        }
    }
}
