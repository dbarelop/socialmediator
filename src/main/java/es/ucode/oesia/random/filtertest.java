package es.ucode.oesia.random;

import es.ucode.oesia.random.domain.SocialNetworkPost;
import es.ucode.oesia.random.service.Classifiers;
import es.ucode.oesia.random.service.FilterByLucene;
import es.ucode.oesia.random.service.FilterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Filtertest
 */
public class Filtertest {
    public static void main(String[] args) {
        Filtertest filtertest = new Filtertest();
        
        //filtertest.testLucene();
        filtertest.testFilterByCategory();
        
    }
    
    public void testFilterByCategory(){
        
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("classifier_data_training/datos_de_deportes").getFile());
        Classifiers classifiers = Classifiers.getClassifiers();
        
        try {
            String content = readFile(file);
            ArrayList<String> array = new ArrayList<>(1);
            array.add(content);
            classifiers.trainClassifier(array.iterator() , "deportes", true);
        } catch (IOException ex) {
            Logger.getLogger(Filtertest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        for(String s : new String[]{
            "Fifa 17",
            "El médico me ha dicho que me he roto el externocleidomastoideo",
            "89",
            "Esto no es un deporte"
        }){
            System.out.println("Probabilidad de "+s+": "+classifiers.getYesProbability(s, "deportes"));
        }
        
        
    }
    
    public void testLucene(){
        List<SocialNetworkPost> list = new ArrayList<>();
        File file = new File("E:\\Users\\abeln\\Desktop\\sample.txt");
        try {
            new BufferedReader(new FileReader(file)).lines().forEach(l -> list.add(new Post(l)));
        } catch (FileNotFoundException ex) {
            System.out.println("No file");
            return;
        }
        
        
        FilterService filter = new FilterByLucene();
        
        filter.filterAll(list, "yes");
        
        list.stream().forEach((next) -> {
            System.out.println(next.getText());
        });
    }
    
    private String readFile(File file) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader (file));
    String         line = null;
    StringBuilder  stringBuilder = new StringBuilder();
    String         ls = System.getProperty("line.separator");

    try {
        while((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }

        return stringBuilder.toString();
    } finally {
        reader.close();
    }
}
    
}


class Post extends SocialNetworkPost{

    public Post(String text) {
        setText(text);
    }
    
}
