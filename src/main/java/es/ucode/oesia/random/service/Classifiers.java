package es.ucode.oesia.random.service;

import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.JointClassifier;
import com.aliasi.util.AbstractExternalizable;
import es.ucode.oesia.random.domain.SocialNetworkPost;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Classifiers
 */
public class Classifiers {
    
    static public final String[] CATEGORIES_YES_NO = new String[]{"yes","no"};
    static public final int NGRAM = 6;
    
    private final List<String> categories;
    
    private final List<JointClassifier<CharSequence>> compiled_clasifiers;
    
    private final List<DynamicLMClassifier> clasifiers;

    
    static private Classifiers singleton = null;
    static public Classifiers getClassifiers(){
        if(singleton==null){
            singleton = new Classifiers();
        }
        return singleton;
    }
    
    
    private Classifiers() {
        this.categories = new ArrayList<>();
        this.compiled_clasifiers = new ArrayList<>();
        this.clasifiers = new ArrayList<>();
    }
    
    
    
    public void trainClassifier(Iterator<String> iterator,String category,boolean yesNo){
        int index = getCategoryIndex(category);
        while(iterator.hasNext()){
            clasifiers.get(index).train(CATEGORIES_YES_NO[yesNo?0:1],iterator.next(),1);
        }
        compileClassifier(index);
    }
    
    public double getYesProbability(CharSequence seq, String category){
        return compiled_clasifiers.get(getCategoryIndex(category)).classify(seq).conditionalProbability(Classifiers.CATEGORIES_YES_NO[0]);
    }
    
    
    private int getCategoryIndex(String category){
        int index = categories.indexOf(category);
        if(index==-1){
            index = categories.size();
            categories.add(category);
            clasifiers.add(DynamicLMClassifier.createNGramBoundary(CATEGORIES_YES_NO,NGRAM));
            compiled_clasifiers.add(null);
            compileClassifier(index);
        }
        return index;
    }
    
    
    private void compileClassifier(int index){
        try {
            compiled_clasifiers.set(index,(JointClassifier<CharSequence>) AbstractExternalizable.compile(clasifiers.get(index)));
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FilterByCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
