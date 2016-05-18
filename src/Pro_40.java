/**
 * Pro_40
 * 数组中只出现一次的数字
 * @author FD
 * @date 2016/5/17
 */
//num1,num2分别为长度为1的数组。传出参数
//将num1[0],num2[0]设置为返回结果
public class Pro_40 {
    public void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
        if (array.length < 2) {
            return;
        }
        int ans = 0;
        for (int i = 0; i < array.length; i++) {
            ans ^= array[i];
        }
        int p = 0;
        while ((ans&1) == 0) {
            ans >>= 1;
            p++;
        }
        for (int i = 0; i < array.length; i++) {
            if ((array[i]&(1<<p)) != 0) {
                num1[0] ^= array[i];
            }
            else {
                num2[0] ^= array[i];
            }
        }
    }
}
