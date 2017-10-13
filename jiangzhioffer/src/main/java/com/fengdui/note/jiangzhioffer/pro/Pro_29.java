import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Pro_29
 * 最小的K个数
 * @author FD
 * @date 2016/5/16
 */
public class Pro_29 {
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        if (k == 0) {
            return new ArrayList<>();
        }
        TreeSet<Integer> set = new TreeSet<>();
        for(int i = 0; i < input.length; i++) {
            if (set.size() < k) {
                set.add(input[i]);
            }
            else {
                if (set.last() > input[i]) {
                    set.remove(set.last());
                    set.add(input[i]);
                }
            }
        }
        Iterator iterator = set.iterator();
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (set.size() < k) {
            return arrayList;
        }
        while (iterator.hasNext()) {
            arrayList.add((Integer)iterator.next());
        }
        return arrayList;
    }
}
