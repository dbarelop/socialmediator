package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkPost;
import java.util.Iterator;
import java.util.List;

public class FilterByCategory implements FilterService{
    
    
    @Override
    public boolean filter(SocialNetworkPost post, String category) {
        return Classifiers.getClassifiers().getYesProbability(post.getText(), category) > 0.5;
    }
    
    @Override
    public void filterAll(List<SocialNetworkPost> posts, String parameter) {
        for (Iterator<SocialNetworkPost> iterator = posts.iterator(); iterator.hasNext();) {
            if(!filter(iterator.next(),parameter)){
                iterator.remove();
            }
        }
    }

}
