package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkPost;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwitterServiceImpl implements SocialNetworkService {

    @Override
    public List<SocialNetworkPost> getLatestPosts() {
        // TODO: implement
        return null;
    }
}
