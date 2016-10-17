import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by fd on 2016/9/19.
 */
public class TestClass {
//    private int m;
//    public int inc(int m) {
//        return m+1;
//    }

    public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader("C:\\Users\\fd\\IdeaProjects\\zheyue\\authClient\\src\\test\\fd2.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null)
                    break;
                System.out.println(line);
            }
            this.getClass().get
            System.out.println(Charset.defaultCharset());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
