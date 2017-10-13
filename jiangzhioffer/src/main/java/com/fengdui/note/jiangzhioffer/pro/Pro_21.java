import java.util.LinkedList;

/**
 * Pro_21
 * 栈的压入、弹出序列
 * @author FD
 * @date 2016/5/16
 */
public class Pro_21 {
    public boolean IsPopOrder(int [] pushA, int [] popA) {
        if (pushA.length == 0 || popA.length == 0) {
            return false;
        }
        LinkedList<Integer> list1 = new LinkedList<Integer>();
        int len1 = 0, len2 = 0;
        while (len2 < popA.length) {
            while (len1 < pushA.length && (list1.isEmpty() || list1.peek() != popA[len2])) {
                list1.push(pushA[len1++]);
            }
            if (list1.isEmpty() || list1.peek() != popA[len2]) {
                break;
            }
            while (!list1.isEmpty() && list1.peek() == popA[len2]) {
                len2++;
                list1.pop();
            }
        }
        if (len2 == popA.length) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int[] a = {1,2,3,4,5};
        int[] b = {4,3,5,1,2};
        new Pro_21().IsPopOrder(a, b);
    }
}
