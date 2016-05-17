import java.util.ArrayList;

/**
 * Pro_33
 * 丑数
 * @author FD
 * @date 2016/5/16
 */
public class Pro_33 {
    public int GetUglyNumber_Solution(int index) {
        if (index == 0) {
            return 0;
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        int sum = 1;
        int p1 = 0, p2 = 0, p3 = 0;
        while (sum < index) {
            int x1 = arrayList.get(p1)*2;
            int x2 = arrayList.get(p2)*3;
            int x3 = arrayList.get(p3)*5;
            int min = Math.min(Math.min(x1, x2), x3);
            if (min == x1)
                p1++;
            if (min == x2)
                p2++;
            if (min == x3)
                p3++;
            arrayList.add(min);
            sum++;
        }
        return arrayList.get(index-1);
    }
}
