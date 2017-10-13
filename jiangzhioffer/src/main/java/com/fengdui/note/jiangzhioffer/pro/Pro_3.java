import java.util.ArrayList;

/**
 * Created by fd on 2016/5/12.
 * 从尾到头打印链表
 */
class ListNode{
    int val;
    ListNode next = null;
    ListNode(int val){
        this.val = val;
    }
}
public class Pro_3 {
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        ListNode preNode = null;
        ListNode currentNode = listNode;

        while (listNode != null) {
            ListNode nextNode = listNode.next;
            listNode.next = preNode;
            preNode = listNode;
            if (nextNode != null) {
                listNode = nextNode;
            }else {
                break;
            }
        }
        while (listNode != null) {
            list.add(listNode.val);
            listNode = listNode.next;
        }
        return list;
    }
}
