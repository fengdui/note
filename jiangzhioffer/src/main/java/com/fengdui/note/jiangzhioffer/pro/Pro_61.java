/**
 * Pro_61
 * 序列化二叉树
 * @author FD
 * @date 2016/5/19
 */
public class Pro_61 {

    String Serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        if (root == null) {
            sb.append("#,");
            return sb.toString();
        }
        sb.append(root.val + ",");
        sb.append(Serialize(root.left));
        sb.append(Serialize(root.right));
        return sb.toString();
    }
    int p = -1;
    TreeNode Deserialize(String str) {
        p++;
        if (p >= str.length())
            return null;
        TreeNode node = null;
        String[] strArray = str.split(",");
        if (!strArray[p].equals("#")) {
            node = new TreeNode(Integer.valueOf(strArray[p]));
            node.left = Deserialize(str);
            node.right = Deserialize(str);
        }
        return node;
    }
}
