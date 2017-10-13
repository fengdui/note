import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Pro_64
 * 滑动窗口的最大值
 * @author FD
 * @date 2016/5/19
 */
public class Pro_64 {
    public ArrayList<Integer> maxInWindows(int[] num, int size) {
        ArrayList<Integer> maxList = new ArrayList<Integer>();
        if(size <= 0) return maxList;
        ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
        for (int i = 0; i < num.length; i++) {
            while (!queue.isEmpty() && i-queue.getFirst() >= size) {
                queue.removeFirst();
            }
            while (!queue.isEmpty() && num[queue.getLast()] <= num[i]) {
                queue.removeLast();
            }
            queue.add(i);
            if(i >= size-1){
                maxList.add(num[queue.getFirst()]);
            }
        }
        return maxList;
    }
}
