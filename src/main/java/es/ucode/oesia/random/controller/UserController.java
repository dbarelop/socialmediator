package es.ucode.oesia.random.controller;

import es.ucode.oesia.random.domain.SocialNetwork;
import es.ucode.oesia.random.repository.UserSocialNetworksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private UserSocialNetworksRepository socialNetworksRepository;

    @GetMapping("/user/socialnetworks")
    public Set<SocialNetwork> userSocialNetworks(Principal principal) {
        Map<SocialNetwork, String> socialNetworksMap = socialNetworksRepository.findByUser(principal.getName());
        return socialNetworksMap != null ? socialNetworksMap.keySet() : new HashSet<>();
    }
}
