package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
public class TwitterServiceImpl implements SocialNetworkService {

    private Map<String, AccessToken> accessTokens = new HashMap<>();

    @Override
    public List<SocialNetworkPost> getLatestPosts(Principal principal) {
        Twitter twitter = TwitterFactory.getSingleton();
        try {
            if (accessTokens.containsKey(principal.getName())) {
                twitter.setOAuthAccessToken(accessTokens.get(principal.getName()));
                List<Status> statuses = twitter.getHomeTimeline();
                for (Status s : statuses) {
                    System.out.println(s.getText());
                }
                // TODO: complete
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isAuthorized(Principal principal) {
        return accessTokens.containsKey(principal.getName());
    }

    public RequestToken getRequestToken() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        RequestToken requestToken = twitter.getOAuthRequestToken();
        return requestToken;
    }

    public void authorize(Principal principal, String oauthToken, String oauthVerifier) throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        AccessToken accessToken = twitter.getOAuthAccessToken(oauthVerifier);
        accessTokens.put(principal.getName(), accessToken);
    }

}
