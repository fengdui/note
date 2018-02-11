import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author FD
 * @version v6.1.0
 * @date 2017/11/9
 */
public class Index {

    private IndexWriter writer;//这个类用来写入索引

    //下面这个类是FileFilter的实现类，用来过滤符合条件的文档。
    private static class TextFilesFilter implements FileFilter {


        @Override
        public boolean accept(File pathname) {
            // TODO Auto-generated method stub
            return pathname.getName().toLowerCase().endsWith(".txt");
        }

    }
    //构造方法，用来传入索引存放路径
    public Index(String indexDir) throws IOException{
        Directory dir=FSDirectory.open(Paths.get(indexDir));
        IndexWriterConfig config=new IndexWriterConfig(new StandardAnalyzer());
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        writer=new IndexWriter(dir,config);
    }
    //关闭indexWriter
    public void close() throws IOException{
        writer.close();
    }
    //这个方法是遍历文件夹下所有文件并选择符合条件文件写入索引的方法，返回写入的文档总数
    public int index(String dataDir,FileFilter filter) throws IOException{
        File[] files=new File(dataDir).listFiles();
        for(File file:files){
            if(!file.isDirectory() && !file.isHidden()
                    && file.exists()
                    && file.canRead()
                    && (filter==null) || filter.accept(file)){
                indexFile(file);
            }
        }
        writer.commit();
        return writer.numDocs();
    }
    //这个方法是写入索引的方法，将生成的document对象写入到索引中
    private void indexFile(File file) throws IOException{
        System.out.println("indexing..."+file.getCanonicalPath());
        Document doc=getDocument(file);
        writer.addDocument(doc);
    }
    //这个方法是生成Document对象的方法，Document对象就是对文档各个属性的封装
    protected Document getDocument(File file) throws IOException{
        Document doc=new Document();
        doc.add(new Field("contents",new FileReader(file), TextField.TYPE_NOT_STORED));
        doc.add(new Field("filename",file.getName(),TextField.TYPE_STORED));
        doc.add(new Field("fullpath",file.getCanonicalPath(),TextField.TYPE_STORED));
        return doc;
    }

    public static void main(String[] args) throws IOException {
        String indexDir="F:/lucene_sy/lucene_index";
        String dataDir="F:/lucene_sy/lucene_data";

        long start=System.currentTimeMillis();
        Index indexer=new Index(indexDir);

        int numberIndexed=indexer.index(dataDir, new TextFilesFilter());

        indexer.close();
        long end=System.currentTimeMillis();
        System.out.println(numberIndexed);
        System.out.println(end-start);
    }
}
