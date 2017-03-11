package es.ucode.oesia.random.service;

import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.JointClassifier;
import es.ucode.oesia.random.domain.SocialNetworkPost;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase Classifiers
 */
public class Classifiers {
    
    static public final String[] categoriesYesNo = new String[]{"yes","no"};
    
    private List<String> categories;
    
    private List<JointClassifier<CharSequence>> clasifiers;
    
    private List<DynamicLMClassifier> raw_clasifiers;

    public Classifiers() {
        this.categories = new LinkedList<>();
        this.clasifiers = new LinkedList<>();
        this.raw_clasifiers = new LinkedList<>();
    }
    
    
    
    
    public void trainClassifier(SocialNetworkPost post,String category,boolean yesNo){
        
    }
    
    public JointClassifier<CharSequence> getClasifierOfCategory(String category){
        
    }
    
    
    private int getCategoryIndex(String category){
        if(categories.indexOf(category)){
            re
        }
    }
    
}
