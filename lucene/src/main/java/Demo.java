import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * @author FD
 * @version v6.1.0
 * @date 2017/11/9
 */
public class Demo {
    public static void main(String[] args) {
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(new File("F:\\lucene_sy"), new StandardAnalyzer(), true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
