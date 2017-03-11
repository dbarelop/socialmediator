package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkPost;
import java.util.Iterator;
import java.util.List;

public class FilterByTaggedUser implements FilterService{
    
    @Override
    public boolean filter(SocialNetworkPost post, String parameter) {
        return post.getTaggedUsers().stream().anyMatch(user -> user.getName().equals(parameter));
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
