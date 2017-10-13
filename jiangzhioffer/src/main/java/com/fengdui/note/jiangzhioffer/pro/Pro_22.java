import java.util.ArrayList;

/**
 * Pro_22
 * 从上往下打印二叉树
 * @author FD
 * @date 2016/5/16
 */
public class Pro_22 {
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        int i = 0, j = 1;
        ArrayList<Integer> list1 = new ArrayList<>();
        if (root == null) {
            return list1;
        }
        list1.add(root.val);

        ArrayList<TreeNode> list2 = new ArrayList<>();
        list2.add(root);
        while (i < j) {
            TreeNode node = list2.get(i);
            if (node.left != null) {
                list1.add(node.left.val);
                list2.add(node.left);
                j++;
            }
            if (node.right != null) {
                list1.add(node.right.val);
                list2.add(node.right);
                j++;
            }
            i++;
        }
        return list1;
    }
}
