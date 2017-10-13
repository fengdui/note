/**
 * Pro_43
 * 左旋转字符串
 * @author FD
 * @date 2016/5/18
 */
public class Pro_43 {
    public String LeftRotateString(String str, int n) {
        if (str.length() < n) {
            return "";
        }
        int m = str.length()-n;
        char[] ch = str.toCharArray();
        reverse(ch, 0, ch.length-1);
        reverse(ch, 0, m-1);
        reverse(ch, m, ch.length-1);
        return new String(ch);
    }
    public void reverse(char[] ch, int start, int end) {
        while (start < end) {
            char c = ch[start];
            ch[start] = ch[end];
            ch[end] = c;
            start++;
            end--;
        }
    }
}
