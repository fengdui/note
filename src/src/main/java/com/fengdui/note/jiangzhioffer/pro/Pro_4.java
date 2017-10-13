/**
 * Created by fd on 2016/5/13.
 * 重建二叉树
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
public class Pro_4 {

    public TreeNode reConstructBinaryTree(int[] pre, int [] in, int start1, int end1, int start2, int end2) {
        if (start1 > end1) {
            return null;
        }
        if (start1 == end1) {
            return new TreeNode(pre[start1]);
        }
        TreeNode leftNode;
        TreeNode rightNode;
        TreeNode rootNode = null;
        for (int i = start2; i <= end2; i++) {
            if (pre[start1] == in[i]) {
                rootNode = new TreeNode(pre[start1]);
                leftNode = reConstructBinaryTree(pre, in, start1+1, start1+i-start2, start2, i-1);
                rightNode = reConstructBinaryTree(pre, in, start1+i-start2+1, end1, i+1, end2);
                rootNode.left = leftNode;
                rootNode.right = rightNode;
                break;
            }
        }
        return rootNode;
    }

    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        TreeNode root =  reConstructBinaryTree(pre, in, 0, pre.length-1, 0, in.length-1);
        return root;
    }

    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6,7}, b = {3,2,4,1,6,5,7};
        new Pro_4().reConstructBinaryTree(a, b);
    }
}
