/**
 * Pro_17
 * 树的子结构
 * @author FD
 * @date 2016/5/13
 */
public class Pro_17 {
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {

        if(root1 == null || root2 == null) {
            return false;
        }
        if (root1.val == root2.val && IsSubtree(root1.left, root2.left) && IsSubtree(root1.right, root2.right)) {
            return true;
        }
        if (HasSubtree(root1.left, root2)) {
            return true;
        }
        if (HasSubtree(root1.right, root2)) {
            return true;
        }
        return false;
    }
    public boolean IsSubtree(TreeNode root1, TreeNode root2) {
        if (root2 == null) {
            return true;
        }
        if (root1 == null) {
            return false;
        }
        if (root1.val == root2.val) {
            if (IsSubtree(root1.left, root2.left) && IsSubtree(root1.right, root2.right)) {
                return true;
            }
        }
        return false;
    }
}
