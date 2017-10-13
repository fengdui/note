/**
 * Pro_38
 * 二叉树的深度
 * @author FD
 * @date 2016/5/17
 */
public class Pro_38 {
    public int TreeDepth(TreeNode pRoot) {
        if (pRoot == null) {
            return 0;
        }
        return Math.max(TreeDepth(pRoot.left), TreeDepth(pRoot.right))+1;
    }
}
