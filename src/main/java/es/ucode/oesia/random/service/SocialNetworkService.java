package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkPost;

import java.security.Principal;
import java.util.List;

public interface SocialNetworkService {

    List<SocialNetworkPost> getLatestPosts(Principal principal);
    boolean isAuthorized(Principal principal);
}
