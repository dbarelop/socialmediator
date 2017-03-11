package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkUser;
import com.aliasi.classify.Classification;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.JointClassification;
import com.aliasi.classify.JointClassifier;
import com.aliasi.util.AbstractExternalizable;
import es.ucode.oesia.random.domain.SocialNetworkPost;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilterByCategory implements FilterService{
    
    private static final String[] CATEGORIES
        = { "games",
            "news",
            "fun" };

    private static final int NGRAM_SIZE = 6;
    
    
    
    
    @Override
    public boolean filter(SocialNetworkPost post, String category) {
        DynamicLMClassifier classifier = DynamicLMClassifier.createNGramBoundary(CATEGORIES,
                        NGRAM_SIZE);
        

        JointClassifier<CharSequence> compiledClassifier;
        try {
            compiledClassifier = (JointClassifier<CharSequence>) AbstractExternalizable.compile(classifier);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FilterByCategory.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }

        
        
        JointClassification jc = compiledClassifier.classify(post.getText());
        return jc.bestCategory().equals(Classifiers.categoriesYesNo[0]);
    }

}
