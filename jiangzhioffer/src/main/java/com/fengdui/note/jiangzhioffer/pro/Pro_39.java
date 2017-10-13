/**
 * Pro_39
 * 平衡二叉树
 * @author FD
 * @date 2016/5/17
 */
public class Pro_39 {
    public boolean IsBalanced_Solution(TreeNode root) {
        if (dfs(root) < 0) {
            return false;
        }
        return true;
    }
    public int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = dfs(root.left), right = dfs(root.right);
        if (left != -1 && right != -1) {
            int dif = left-right;
            if (dif < -1 || dif > 1) {
                return -1;
            }
            int depth = (left > right ? left:right)+1;
            return depth;
        }
        return -1;
    }
}
