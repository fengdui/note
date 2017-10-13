/**
 * Pro_62
 * 二叉搜索树的第k个结点
 * @author FD
 * @date 2016/5/19
 */
public class Pro_62 {
    int count = 0;
    TreeNode KthNode(TreeNode pRoot, int k) {
        if (pRoot == null) {
            return null;
        }
        TreeNode node = KthNode(pRoot.left, k);
        if (node != null) {
            return node;
        }
        if (++count == k) {
            return pRoot;
        }
        node = KthNode(pRoot.right, k);
        if (node != null) {
            return node;
        }
        return null;
    }
}
