package es.ucode.oesia.random.controller;

import es.ucode.oesia.random.domain.SocialNetwork;
import es.ucode.oesia.random.domain.SocialNetworkPost;
import es.ucode.oesia.random.service.FacebookServiceImpl;
import es.ucode.oesia.random.service.GoogleServiceImpl;
import es.ucode.oesia.random.service.SocialNetworkAggregatorService;
import es.ucode.oesia.random.service.TwitterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class TimelineController {

    @Autowired
    private SocialNetworkAggregatorService socialNetworksService;
    @Autowired
    private TwitterServiceImpl twitterService;
    @Autowired
    private FacebookServiceImpl facebookService;
    @Autowired
    private GoogleServiceImpl googleService;

    @GetMapping("/posts/latest")
    public List<SocialNetworkPost> getLatestPosts(Principal principal,
                                                  @RequestParam(value = "socialNetwork", required = false) SocialNetwork socialNetwork,
                                                  @RequestParam(value = "tag", required = false) String tag) {
        if (socialNetwork != null) {
            switch (socialNetwork) {
                case twitter:
                    return tag != null ? twitterService.getLatestPostsByTag(principal, tag) : twitterService.getLatestPosts(principal);
                case facebook:
                    return tag != null ? facebookService.getLatestPostsByTag(principal, tag) : facebookService.getLatestPosts(principal);
                case google:
                    return tag != null ? googleService.getLatestPostsByTag(principal, tag) :  googleService.getLatestPosts(principal);
            }
        }
        return tag != null ? socialNetworksService.getLatestPostsByTag(principal, tag) : socialNetworksService.getLatestPosts(principal);
    }

    @GetMapping("/posts/page/{page}")
    public List<SocialNetworkPost> getPosts(Principal principal, @PathVariable("page") int page) {
        return socialNetworksService.getPosts(principal, page);
    }

}
