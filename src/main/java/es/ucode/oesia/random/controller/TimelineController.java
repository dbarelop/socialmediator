package es.ucode.oesia.random.controller;

import es.ucode.oesia.random.domain.SocialNetworkPost;
import es.ucode.oesia.random.service.SocialNetworkAggregatorService;
import es.ucode.oesia.random.service.TwitterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class TimelineController {

    //@Autowired
    private SocialNetworkAggregatorService socialNetworksService;
    @Autowired
    private TwitterServiceImpl twitterService;

    @GetMapping("/posts/latest")
    public List<SocialNetworkPost> getLatestPosts() {
        return socialNetworksService.getLatestPosts();
    }

    @GetMapping("/posts/{socialNetwork}/latest")
    public List<SocialNetworkPost> getLatestPosts(Principal principal, @PathVariable("socialNetwork") String socialNetwork) {
        // TODO: implement
        if (socialNetwork.equals("twitter")) {
            return twitterService.getLatestPosts(principal);
        }
        return null;
    }
}
