import java.util.HashSet;
import java.util.Set;

/**
 * Pro_55
 * 链表中环的入口结点
 * @author FD
 * @date 2016/5/18
 */
public class Pro_55 {
    public ListNode EntryNodeOfLoop(ListNode pHead) {
        Set<ListNode> set = new HashSet<ListNode>();
        while(pHead!= null && set.add(pHead)){
            pHead = pHead.next;
        }
        return pHead;
    }
}
