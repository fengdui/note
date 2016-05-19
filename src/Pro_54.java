import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pro_54
 * 字符流中第一个不重复的字符
 * @author FD
 * @date 2016/5/18
 */
public class Pro_54 {
    private Map<Character, Integer> map = new HashMap<Character, Integer>();
    private List<Character> list = new ArrayList<Character>();

    public void Insert(char ch) {
        if(!map.containsKey(ch)){
            map.put(ch, 1);
            list.add(ch);
        }else{
            map.put(ch, map.get(ch) + 1);
            if(list.contains(ch)){
                list.remove(Character.valueOf(ch));
            }
        }
    }
    public char FirstAppearingOnce() {
        if(list.isEmpty()) return '#';
        return list.get(0);
    }
}
