/**
 * Pro_45
 * 扑克牌顺子
 * @author FD
 * @date 2016/5/18
 */
public class Pro_45 {
    public boolean isContinuous(int [] numbers) {
        if (numbers.length < 5) {
            return false;
        }
        int[] a = new int[14];
        for (int i = 0; i < numbers.length; i++) {
            a[numbers[i]]++;
        }
        int pre = -1;
        int sum = a[0];
        for (int i = 1; i <= 13; i++) {
            if (a[i] > 1) {
                return false;
            }
            if (a[i] == 0) {
                continue;
            }
            if (pre == -1) {
                pre = i;
            }
            else {
                sum -= i-pre-1;
                pre = i;
            }
        }
        return sum >= 0 ? true : false;
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 2, 5, 4};
        System.out.println(new Pro_45().isContinuous(a));
    }
}
