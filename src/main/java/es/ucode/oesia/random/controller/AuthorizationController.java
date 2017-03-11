package es.ucode.oesia.random.controller;

import es.ucode.oesia.random.service.TwitterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> requestAuthorization(Principal principal, @PathVariable("socialNetwork") String socialNetwork) throws TwitterException {
        if (socialNetwork.equals("twitter")) {
            if (twitterService.isAuthorized(principal)) {
                return new ResponseEntity<>("Already authorized", HttpStatus.OK);
            } else {
                RequestToken requestToken = twitterService.getRequestToken();
                pendingAuthorizations.put(principal.getName(), requestToken);
                String url = requestToken.getAuthorizationURL();
                System.out.println(url);
                return new ResponseEntity<>(new AuthorizationURL(url), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/auth/{socialNetwork}/callback")
    public ResponseEntity<Void> doAuthorize(Principal principal,
                              @PathVariable("socialNetwork") String socialNetwork,
                              @RequestParam("oauth_token") String oauthToken,
                              @RequestParam("oauth_verifier") String oauthVerifier) throws TwitterException {
        if (socialNetwork.equals("twitter")) {
            twitterService.authorize(principal, oauthToken, oauthVerifier);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    private class AuthorizationURL {
        private String url;

        AuthorizationURL(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

}
