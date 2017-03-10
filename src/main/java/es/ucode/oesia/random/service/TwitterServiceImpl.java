package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwitterServiceImpl implements SocialNetworkService {

    @Override
    public List<Post> getLatestPosts() {
        // TODO: implement
        return null;
    }
}
