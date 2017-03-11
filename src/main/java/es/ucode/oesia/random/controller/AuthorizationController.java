package es.ucode.oesia.random.controller;

import es.ucode.oesia.random.service.TwitterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthorizationController {
    // TODO: complete

    @Autowired
    private TwitterServiceImpl twitterService;

    private Map<String, RequestToken> pendingAuthorizations = new HashMap<>();

    @GetMapping("/auth/{socialNetwork}")
    public String requestAuthorization(Principal principal, @PathVariable("socialNetwork") String socialNetwork) throws TwitterException {
        if (socialNetwork.equals("twitter")) {
            if (twitterService.isAuthorized(principal)) {
                return "Already authorized";
            } else {
                RequestToken requestToken = twitterService.getRequestToken();
                pendingAuthorizations.put(principal.getName(), requestToken);
                return requestToken.getAuthorizationURL();
            }
        }
        return null;
    }

    @PostMapping("/auth/{socialNetwork}")
    public String doAuthorize(Principal principal, @PathVariable("socialNetwork") String socialNetwork, @ModelAttribute("pin") String pin) throws TwitterException {
        if (socialNetwork.equals("twitter")) {
            twitterService.authorize(principal, pendingAuthorizations.get(principal.getName()), pin);
            return "OK";
        }
        return null;
    }
}
