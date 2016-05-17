/**
 * Pro_25
 * 复杂链表的复制
 * @author FD
 * @date 2016/5/16
 */
class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}
public class Pro_25 {
    public RandomListNode Clone(RandomListNode pHead) {
        RandomListNode pHead2 = new RandomListNode(pHead.label);
        RandomListNode pheadCopy = pHead;
        while (pHead.next != null) {
            pHead = pHead.next;
            pHead2.next = new RandomListNode(pHead.label);
        }
        return pHead;
    }

}
