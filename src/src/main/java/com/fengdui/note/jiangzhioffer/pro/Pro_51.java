/**
 * Pro_51
 * 构建乘积数组
 * @author FD
 * @date 2016/5/18
 */
public class Pro_51 {
    public int[] multiply(int[] A) {
        int n = A.length;
        int[] front = new int[n];
        if(n <= 1) return front;
        int[] back = new int[n];
        front[0] = back[n - 1] = 1;
        for(int i = 1; i < n; i++){
            front[i] = front[i - 1] * A[i - 1];
            back[n - 1 - i] = back[n - i] * A[n - i];
        }
        for(int i = 0; i < n; i++){
            front[i] *= back[i];
        }
        return front;
    }
}
