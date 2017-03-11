package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetwork;
import es.ucode.oesia.random.domain.SocialNetworkPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SocialNetworkAggregatorService  implements SocialNetworkService {
    @Autowired
    private FacebookServiceImpl facebookService;
    @Autowired
    private GoogleServiceImpl googleService;
    @Autowired
    private TwitterServiceImpl twitterService;

    @Override
    public List<SocialNetworkPost> getLatestPosts(Principal principal) {
        List<SocialNetworkPost> res = new ArrayList<>();
        List<SocialNetworkPost> facebook = facebookService.getLatestPosts(principal);
        List<SocialNetworkPost> google = googleService.getLatestPosts(principal);
        List<SocialNetworkPost> twitter = twitterService.getLatestPosts(principal);
        if (facebook != null) {
            res.addAll(facebook);
        }
        if (google != null) {
            res.addAll(google);
        }
        if (twitter != null) {
            res.addAll(twitter);
        }
        return res;
    }

    @Override
    public List<SocialNetworkPost> getLatestPostsByTag(Principal principal, String tag) {
        List<SocialNetworkPost> res = new ArrayList<>();
        List<SocialNetworkPost> facebook = facebookService.getLatestPostsByTag(principal, tag);
        List<SocialNetworkPost> google = googleService.getLatestPostsByTag(principal, tag);
        List<SocialNetworkPost> twitter = twitterService.getLatestPostsByTag(principal, tag);
        if (facebook != null) {
            res.addAll(facebook);
        }
        if (google != null) {
            res.addAll(google);
        }
        if (twitter != null) {
            res.addAll(twitter);
        }
        return res;
    }

    @Override
    public boolean isAuthorized(Principal principal) {
        return facebookService.isAuthorized(principal) || googleService.isAuthorized(principal) || twitterService.isAuthorized(principal);
    }
}
