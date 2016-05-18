import java.util.ArrayList;

/**
 * Pro_42
 * 和为S的两个数字
 * @author FD
 * @date 2016/5/17
 */
public class Pro_42 {
    public ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
        int j = array.length-1;
        int ans1 = 0, ans2 = 0;
        for (int i = 0; i < array.length && i < j; i++) {
            while (i < j && array[i] + array[j] > sum) {
                j--;
            }
            if (i < j && array[i] + array[j] == sum) {
                if (ans1 == ans2) {
                    ans1 = array[i];
                    ans2 = array[j];
                }
                else {
                    if (ans1*ans2 > array[i]*array[j]) {
                        ans1 = array[i];
                        ans2 = array[j];
                    }
                }
            }
        }
        ArrayList<Integer> list = new ArrayList();
        if (ans1 == ans2) {
            return list;
        }
        list.add(ans1);
        list.add(ans2);
        return list;
    }
}
