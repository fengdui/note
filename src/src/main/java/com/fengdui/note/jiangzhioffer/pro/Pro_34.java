import java.util.HashMap;
import java.util.Map;

/**
 * Pro_34
 * 第一个只出现一次的字符位置
 * @author FD
 * @date 2016/5/17
 */
public class Pro_34 {
    public int FirstNotRepeatingChar(String str) {
        if (str == null || str.length() == 0) {
            return -1;
        }
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            if (map.get(str.charAt(i)) == null) {
                map.put(str.charAt(i), 1);
            }
            else {
                int value = map.get(str.charAt(i));
                map.put(str.charAt(i), value+1);
            }
        }
        for (int i = 0; i < str.length(); i++) {
            if (map.get(str.charAt(i)) == 1) {
                return i;
            }
        }
        return -1;
    }
}
