package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.Post;

import java.util.List;

public interface SocialNetworkService {

    List<Post> getLatestPosts();
}
