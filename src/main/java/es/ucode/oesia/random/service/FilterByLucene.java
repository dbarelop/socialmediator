package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkPost;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;

/**
 * Clase FilterByLucene
 */
public class FilterByLucene implements FilterService{

    @Override
    public boolean filter(SocialNetworkPost post, String parameter) {
        return true;
    }

    @Override
    public void filterAll(List<SocialNetworkPost> posts, String parameter) {
        try {
            Analyzer analyzer = new StandardAnalyzer();
            
            // Store the index in memory:
            Directory directory = new RAMDirectory();
            // To store an index on disk, use this instead:
            //Directory directory = FSDirectory.open("/tmp/testindex");
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter iwriter = new IndexWriter(directory, config);
            for (int i = 0; i < posts.size(); i++) {
                SocialNetworkPost next = posts.get(i);
                Document doc = new Document();
                doc.add(new Field("text", next.getText(), TextField.TYPE_STORED));
                doc.add(new Field("index", Integer.toString(i), TextField.TYPE_STORED));
                iwriter.addDocument(doc);
            }
            iwriter.close();
            
            // Now search the index:
            DirectoryReader ireader = DirectoryReader.open(directory);
            IndexSearcher isearcher = new IndexSearcher(ireader);
            // Parse a simple query that searches for "text":
            Query query = new FuzzyQuery(new Term("text", parameter));
            //QueryParser parser = new QueryParser("text", analyzer);
            /*
            try {
                query = parser.parse(parameter);
            } catch (ParseException ex) {
                Logger.getLogger(FilterByLucene.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            */
            
            ScoreDoc[] hits = isearcher.search(query, 10, Sort.INDEXORDER).scoreDocs;
            // Iterate through the results:
            int toRemove = posts.size()-1;
            for (int i = hits.length-1; i >= 0; i--) {
                int keep = Integer.parseInt(isearcher.doc(hits[i].doc).getField("index").stringValue());
                while(toRemove > keep){
                    posts.remove(toRemove);
                    toRemove--;
                }
                toRemove--;
            }
            while(toRemove >= 0){
                posts.remove(toRemove);
                toRemove--;
            }
            
            
            ireader.close();
            directory.close();
        } catch (IOException ex) {
            Logger.getLogger(FilterByLucene.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
