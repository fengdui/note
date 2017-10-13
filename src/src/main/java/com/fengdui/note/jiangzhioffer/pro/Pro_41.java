import java.util.ArrayList;

/**
 * Pro_41
 * 和为S的连续正数序列
 * @author FD
 * @date 2016/5/17
 */
public class Pro_41 {
    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> arrayLists = new ArrayList<>();
        int start = 1, end = 2, ans = 3;
        while (start < (sum+1)/2) {

            if (sum == ans) {
                ArrayList<Integer> list = new ArrayList<>();
                for (int i = start; i <= end; i++) {
                    list.add(i);
                }
                arrayLists.add(list);
            }
            end++;
            ans += end;
            while (start < (sum+1)/2 && sum < ans) {
                ans -= start;
                start++;
            }
        }
        return arrayLists;
    }
}
