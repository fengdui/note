/**
 * Pro_18
 * 二叉树的镜像
 * @author FD
 * @date 2016/5/16
 */
public class Pro_18 {
    public void Mirror(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = right;
        root.right = left;
        Mirror(root.left);
        Mirror(root.right);
    }
}
