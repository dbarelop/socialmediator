package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class SocialNetworkAggregatorService  implements SocialNetworkService {
    static final int PAGE_SIZE = 20;
    
    @Autowired
    private FacebookServiceImpl facebookService;
    @Autowired
    private GoogleServiceImpl googleService;
    @Autowired
    private TwitterServiceImpl twitterService;

    /**
     * Returns a list of the last created [PAGE_SIZE] SocialNetworkPosts
     */
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
        res.sort(Comparator.comparing(SocialNetworkPost::getDate));
        return res.stream().limit(PAGE_SIZE).collect(Collectors.toList());
    }

    /**
     * Returns a list of SocialNetworkPost objects ordered by creation date and
     * corresponding to the specified page (aggregated pagination)
     */
    @Override
    public List<SocialNetworkPost> getPosts(Principal principal, int page) {
        // TODO: very slow!!
        List<SocialNetworkPost> res = new ArrayList<>();
        boolean[] noMorePosts = new boolean[]{ false, false, false };
        int i = 1;
        while (i++ <= page) {
            List<SocialNetworkPost> facebook = !noMorePosts[0] ? facebookService.getPosts(principal, (i - 1) * PAGE_SIZE) : null;
            List<SocialNetworkPost> google = !noMorePosts[1] ? googleService.getPosts(principal, i) : null;
            List<SocialNetworkPost> twitter = !noMorePosts[2] ? twitterService.getPosts(principal, i) : null;
            if (facebook != null) {
                res.addAll(facebook);
            }
            if (google != null) {
                res.addAll(google);
            }
            if (twitter != null) {
                res.addAll(twitter);
            }
            noMorePosts = new boolean[]{ facebook == null, google == null, twitter == null };
        }
        res.sort(Comparator.comparing(SocialNetworkPost::getDate));
        return res.stream().skip(PAGE_SIZE * (page - 1)).limit(PAGE_SIZE).collect(Collectors.toList());
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
