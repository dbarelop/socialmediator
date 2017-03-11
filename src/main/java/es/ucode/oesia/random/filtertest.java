package es.ucode.oesia.random;

import es.ucode.oesia.random.domain.SocialNetworkPost;
import es.ucode.oesia.random.service.FilterByLucene;
import es.ucode.oesia.random.service.FilterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase filtertest
 */
public class filtertest {
    public static void main(String[] args) {
        List<SocialNetworkPost> list = new ArrayList<>();
        File file = new File("E:\\Users\\abeln\\Desktop\\sample.txt");
        try {
            new BufferedReader(new FileReader(file)).lines().forEach(l -> list.add(new Post(l)));
        } catch (FileNotFoundException ex) {
            System.out.println("No file");
            return;
        }
        
        
        FilterService filter = new FilterByLucene();
        
        filter.filterAll(list, "games");
        
        list.stream().forEach((next) -> {
            System.out.println(next.getText());
        });
    }
}


class Post extends SocialNetworkPost{

    public Post(String text) {
        setText(text);
    }
    
}
