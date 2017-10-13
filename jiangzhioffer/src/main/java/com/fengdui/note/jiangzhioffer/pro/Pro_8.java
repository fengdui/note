/**
 * Created by fd on 2016/5/13.
 * 跳台阶
 */
public class Pro_8 {
    public int JumpFloor(int target) {
        if(target == 0){
            return 0;
        }else if(target == 1){
            return 1;
        } else if(target == 2) {
            return 2;
        }

        int one = 1;
        int two = 2;
        int sum = 0;
        for (int i = 3; i <= target; i++) {
            sum = one + two;
            one = two;
            two = sum;
        }
        return sum;
    }
}
