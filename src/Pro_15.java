/**
 * Pro_15
 * 反转链表
 * @author FD
 * @date 2016/5/13
 */
public class Pro_15 {
    public ListNode ReverseList(ListNode head) {
        ListNode preNode = null;
        ListNode currentNode = head;

        while (head != null) {
            ListNode nextNode = head.next;
            head.next = preNode;
            preNode = head;
            if (nextNode != null) {
                head = nextNode;
            }else {
                break;
            }
        }
        return head;
    }
}
