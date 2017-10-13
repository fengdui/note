/**
 * Pro_14
 * 链表中倒数第k个结点
 * @author FD
 * @date 2016/5/13
 */

public class Pro_14 {
    public ListNode FindKthToTail(ListNode head,int k) {
        int pos = 0;
        ListNode currentNode = head;
        while (pos < k) {
            if (head == null) {
                return null;
            }
            pos++;
            head = head.next;
        }
        if (pos < k-1) {
            return null;
        }
        while (head != null) {
            head = head.next;
            currentNode = currentNode.next;
        }
        return currentNode;
    }
}
