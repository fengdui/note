/**
 * Created by fd on 2016/5/12.
 * 替换空格
 */
public class Pro_2 {
    public String replaceSpace(StringBuffer str) {
        int num = 0;
        char[] ch = new char[str.length()];
        str.getChars(0, str.length(), ch, 0);
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == ' ') {
                num++;
            }
        }
        char[] ans = new char[str.length()+num*2];
        int len = str.length()-1, n = ans.length-1;
        while (len >= 0) {
            if (ch[len] == ' ') {
                ans[n--] = '0';
                ans[n--] = '2';
                ans[n--] = '%';
            }
            else {
                ans[n--] = ch[len];
            }
            len--;
        }
        return new String(ans);
    }

    public static void main(String[] args) {
        new Pro_2().replaceSpace(new StringBuffer("XXX  XX"));
    }
}
