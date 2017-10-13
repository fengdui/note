/**
 * Pro_36
 * 两个链表的第一个公共结点
 * @author FD
 * @date 2016/5/17
 */
public class Pro_36 {
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        int length1 = 0, length2 = 0;
        ListNode head1 = pHead1, head2 = pHead2;
        while (head1 != null) {
            length1++;
            head1 = head1.next;
        }
        while (head2 != null) {
            length2++;
            head2 = head2.next;
        }
        if (length1 == 0 || length2 == 0) {
            return null;
        }
        if (length1 >= length2) {
            int x = length1-length2;
            while (x-- > 0) {
                pHead1 = pHead1.next;
            }
        }
        else if (length1 < length2) {
            int x = length2-length1;
            while (x-- > 0) {
                pHead2 = pHead2.next;
            }
        }
        while (pHead1 != null && pHead2 != null) {
            if (pHead1.val == pHead2.val) {
                return pHead1;
            }
            pHead1 = pHead1.next;
            pHead2 = pHead2.next;
        }
        return null;
    }
}
