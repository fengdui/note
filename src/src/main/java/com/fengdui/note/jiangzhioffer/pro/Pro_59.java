import java.util.ArrayList;

/**
 * Pro_59
 * 按之字形顺序打印二叉树
 * @author FD
 * @date 2016/5/18
 */
public class Pro_59 {
    ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> arrayLists = new ArrayList<>();
        int i = 0, j = 1;
        ArrayList<Integer> list1 = new ArrayList<>();
        if (pRoot == null) {
            return arrayLists;
        }
        list1.add(pRoot.val);
        arrayLists.add(list1);

        ArrayList<TreeNode> list2 = new ArrayList<>();
        list2.add(pRoot);
        int sum = 0;
        list1 = new ArrayList<>();
        while (i < j) {
            TreeNode node = list2.get(i);
            if (node.left != null) {
                list1.add(node.left.val);
                list2.add(node.left);
                sum++;
            }
            if (node.right != null) {
                list1.add(node.right.val);
                list2.add(node.right);
                sum++;
            }
            i++;
            if (i == j && !list1.isEmpty()) {
                j += sum;
                arrayLists.add(list1);
                list1 = new ArrayList<>();
                sum = 0;
            }
        }
        return arrayLists;
    }
}
