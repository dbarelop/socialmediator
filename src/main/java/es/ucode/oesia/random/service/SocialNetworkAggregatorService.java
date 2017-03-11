package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkPost;

import java.security.Principal;
import java.util.List;

public class SocialNetworkAggregatorService  implements SocialNetworkService {
    // TODO: implement

    @Override
    public List<SocialNetworkPost> getLatestPosts(Principal principal) {
        return null;
    }

    @Override
    public List<SocialNetworkPost> getLatestPostsByTag(Principal principal, String tag) {
        return null;
    }

    @Override
    public boolean isAuthorized(Principal principal) {
        return false;
    }
}
