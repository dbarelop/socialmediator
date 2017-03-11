package es.ucode.oesia.random.controller.auth;

import es.ucode.oesia.random.service.TwitterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.TwitterException;

import java.net.URI;
import java.security.Principal;

@RestController
public class TwitterAuthorizationController {

    @Autowired
    private TwitterServiceImpl twitterService;

    @GetMapping("/auth/twitter")
    public ResponseEntity<?> requestAuthorization(Principal principal) throws TwitterException {
        if (twitterService.isAuthorized(principal)) {
            return new ResponseEntity<>("Already authorized", HttpStatus.OK);
        } else {
            String url = twitterService.getRequestToken().getAuthorizationURL();
            return new ResponseEntity<>(new AuthorizationURL(url), HttpStatus.OK);
        }
    }

    @GetMapping("/auth/twitter/callback")
    public ResponseEntity<Void> doAuthorize(Principal principal,
                                            @RequestParam("oauth_token") String oauthToken,
                                            @RequestParam("oauth_verifier") String oauthVerifier) throws TwitterException {
        twitterService.authorize(principal, oauthVerifier);
        HttpHeaders h = new HttpHeaders();
        h.setLocation(URI.create("/"));
        return new ResponseEntity<>(h, HttpStatus.TEMPORARY_REDIRECT);
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
