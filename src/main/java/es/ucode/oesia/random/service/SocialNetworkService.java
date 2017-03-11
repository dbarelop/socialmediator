package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkPost;

import java.security.Principal;
import java.util.List;

public interface SocialNetworkService {

    List<SocialNetworkPost> getLatestPosts(Principal principal);

    List<SocialNetworkPost> getPosts(Principal principal, int page);

    List<SocialNetworkPost> getLatestPostsByTag(Principal principal, String tag);
    boolean isAuthorized(Principal principal);
}
