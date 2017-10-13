/**
 * Pro_57
 * 二叉树的下一个结点
 * @author FD
 * @date 2016/5/18
 */

class TreeLinkNode {
    int val;
    TreeLinkNode left = null;
    TreeLinkNode right = null;
    TreeLinkNode next = null;

    TreeLinkNode(int val) {
        this.val = val;
    }
}
public class Pro_57 {
    public TreeLinkNode GetNext(TreeLinkNode pNode) {
        if (pNode == null) {
            return null;
        }
        while (pNode.left != null) {
            pNode = pNode.left;
        }
        return pNode;
    }
}
