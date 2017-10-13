/**
 * Created by fd on 2016/5/12.
 * 二维数组中的查找 
 */
public class Pro_1 {
    public boolean Find(int [][] array,int target) {
        int n = array.length, m = array[0].length;
        int i = n-1, j = 0;
        while (i >= 0 && j < m) {
            if (array[i][j] > target) {
                i--;
            }
            else if (array[i][j] < target) {
                j++;
            }
            else if (array[i][j] == target) {
                return true;
            }
        }
        return false;
    }

}
