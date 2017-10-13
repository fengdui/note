/**
 * Pro_23
 * 二叉搜索树的后序遍历序列
 * @author FD
 * @date 2016/5/16
 */
public class Pro_23 {
    public boolean VerifySquenceOfBST(int [] sequence) {
        if (sequence == null || sequence.length == 0) {
            return false;
        }
        int pos = 0, n = sequence.length;
        return check(sequence, 0, n-1);
    }
    public boolean check(int [] sequence, int l, int r) {
        if (l >= r) {
            return true;
        }
        int pos = l-1;
        int p = l;
        while (pos < r && sequence[++pos] < sequence[r]) {
            p = pos;
        }
        while (pos < r && sequence[++pos] > sequence[r]) {

        }
        if (pos < r-1) {
            return false;
        }
        return check(sequence, l, p) && check(sequence, p+1, r-1);
    }
}
