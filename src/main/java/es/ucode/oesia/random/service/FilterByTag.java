package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkPost;

public class FilterByTag implements FilterService{

    @Override
    public boolean filter(SocialNetworkPost post, String tag) {
        return post.getTags().contains(tag);
    }
}
