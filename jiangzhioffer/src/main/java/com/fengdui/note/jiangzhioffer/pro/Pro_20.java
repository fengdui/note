import java.util.LinkedList;

/**
 * Pro_20
 * 包含min函数的栈
 * @author FD
 * @date 2016/5/16
 */
public class Pro_20 {
    LinkedList<Integer> list1 = new LinkedList();
    LinkedList<Integer> list2 = new LinkedList();
    public void push(int node) {
        list1.push(node);
        if (list2.isEmpty() || list2.peek() > node) {
            list2.push(node);
        }
        else {
            list2.push(list2.peek());
        }
    }

    public void pop() {
        list1.pop();
        list2.pop();
    }

    public int top() {
        return list1.peek();
    }

    public int min() {
        return list2.peek();
    }
}
