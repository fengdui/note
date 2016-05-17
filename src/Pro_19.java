import java.util.ArrayList;
import java.util.Arrays;

/**
 * Pro_19
 * 顺时针打印矩阵
 * @author FD
 * @date 2016/5/16
 */
public class Pro_19 {
    public ArrayList<Integer> printMatrix(int [][] matrix) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        int n = matrix.length;
        int m = matrix[0].length;
        boolean[][] a = new boolean[n][m];
        int sum = n*m;
        int column = 0, row = 0;
        while (sum > 0) {
            boolean flag = false;
            while (sum > 0 && column < m && !a[row][column]) {
                list.add(matrix[row][column]);
                a[row][column] = true;
                column++;
                sum--;
                flag = true;
            }
            if (flag) {
                column--;
                row++;
            }
            flag = false;
            while (sum > 0 && row < n && !a[row][column]) {
                list.add(matrix[row][column]);
                a[row][column] = true;
                row++;
                sum--;
                flag = true;
            }
            if (flag) {
                row--;
                column--;
            }
            flag = false;
            while (sum > 0 && column >= 0 && !a[row][column]) {
                list.add(matrix[row][column]);
                a[row][column] = true;
                column--;
                sum--;
                flag = true;
            }
            if (flag) {
                column++;
                row--;
            }
            flag = false;
            while (sum > 0 && row >= 0 && !a[row][column]) {
                list.add(matrix[row][column]);
                a[row][column] = true;
                row--;
                sum--;
                flag = true;
            }
            if (flag) {
                row++;
                column++;
            }
            flag = false;
        }
        return list;
    }

    public static void main(String[] args) {
        int[][] a = new int[3][3];
        a[0][0] = 1;
        a[0][1] = 2;
        a[0][2] = 3;
        a[1][0] = 4;
        a[1][1] = 5;
        a[1][2] = 6;
        a[2][0] = 7;
        a[2][1] = 8;
        a[2][2] = 9;

        System.out.println(new Pro_19().printMatrix(a));
    }
}
