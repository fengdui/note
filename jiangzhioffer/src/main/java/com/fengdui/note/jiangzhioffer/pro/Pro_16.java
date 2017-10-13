/**
 * Pro_16
 * 合并两个排序的链表
 * @author FD
 * @date 2016/5/13
 */
public class Pro_16 {
    public ListNode Merge(ListNode list1,ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        else if (list2 == null) {
            return list1;
        }
        else if (list1 == null && list2 == null) {
            return null;
        }
        ListNode head = list1;
        if (list1.val > list2.val) {
            head = list2;
        }
        ListNode pre = null;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                ListNode next1 = list1.next;
                ListNode next2 = list2.next;
                list1.next = list2;
                list2.next = next1;
                pre = list2;
                list1 = next1;
                list2 = next2;
            }
            else {
                ListNode next1 = list1.next;
                ListNode next2 = list2.next;
                if (pre != null) {
                    pre.next = list2;
                }
                list2.next = list1;
                list1.next = next2;
                pre = list1;
                list1 = next1;
                list2 = next2;
            }
        }
        if (list1 != null) {
            pre.next = list1;
        }
        if (list2 != null) {
            pre.next = list2;
        }
        return head;
    }
}
