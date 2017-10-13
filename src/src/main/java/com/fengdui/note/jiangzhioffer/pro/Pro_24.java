import java.util.ArrayList;
import java.util.Collections;

/**
 * Pro_24
 * 二叉树中和为某一值的路径
 * @author FD
 * @date 2016/5/16
 */
public class Pro_24 {
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        FindPath(root, target, 0, ans, path);
        return ans;
    }
    public void FindPath(TreeNode root, int target, int sum, ArrayList<ArrayList<Integer>> ans, ArrayList<Integer> path) {
        if (root == null) {
            return;
        }
        path.add(root.val);
        sum += root.val;
        if (root.left == null && root.right == null) {
            if (target == sum) {
                ArrayList<Integer> dest = new ArrayList<>();
                dest.addAll(path);
                ans.add(dest);
            }
            path.remove(path.size()-1);
            return;
        }
        FindPath(root.left, target, sum, ans, path);
        FindPath(root.right, target, sum, ans, path);
        path.remove(path.size()-1);
    }
}
