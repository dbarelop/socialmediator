package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkPost;
import java.util.Iterator;
import java.util.List;

public class FilterByTag implements FilterService{

    @Override
    public boolean filter(SocialNetworkPost post, String tag) {
        return post.getTags().contains(tag);
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
