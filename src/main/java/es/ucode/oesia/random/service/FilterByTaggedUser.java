package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkPost;

public class FilterByTaggedUser implements FilterService{
    
    @Override
    public boolean filter(SocialNetworkPost post, String parameter) {
        return post.getTaggedUsers().stream().anyMatch(user -> user.getName().equals(parameter));
    }
}
