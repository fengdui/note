/**
 * Pro_44
 * 翻转单词顺序列
 * @author FD
 * @date 2016/5/18
 */
public class Pro_44 {
    public String ReverseSentence(String str) {
        char[] ch = str.toCharArray();
        reverse(ch, 0, ch.length-1);
        int j = 0;
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == ' ') {
                reverse(ch, j, i-1);
                j = i+1;
            }
        }
        reverse(ch, j, ch.length-1);
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
