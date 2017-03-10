package es.ucode.oesia.random.controller;

import es.ucode.oesia.random.domain.Post;
import es.ucode.oesia.random.service.SocialNetworkAggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TimelineController {

    //@Autowired
    private SocialNetworkAggregatorService socialNetworksService;

    @GetMapping("/test")
    public String test() {
        return "ok";
    }

    @GetMapping("/posts/latest")
    public List<Post> getLatestPosts() {
        return socialNetworksService.getLatestPosts();
    }

    @GetMapping("/posts/{socialNetwork}/latest")
    public List<Post> getLatestPosts(@PathVariable("socialNetwork") String socialNetwork) {
        // TODO: implement
        return null;
    }
}
