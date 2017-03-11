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

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
public class TwitterServiceImpl implements SocialNetworkService {

    private Twitter twitter = TwitterFactory.getSingleton();

    private Map<String, AccessToken> accessTokens = new HashMap<>();

    @Override
    public List<SocialNetworkPost> getLatestPosts(Principal principal) {
        try {
            if (accessTokens.containsKey(principal.getName())) {
                twitter.setOAuthAccessToken(accessTokens.get(principal.getName()));
                List<Status> statuses = twitter.getHomeTimeline();
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
        RequestToken requestToken = twitter.getOAuthRequestToken();
        return requestToken;
    }

    public void authorize(Principal principal, RequestToken requestToken, String pin) throws TwitterException {
        AccessToken accessToken;
        if (pin.length() > 0) {
            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
        } else {
            accessToken = twitter.getOAuthAccessToken();
        }
        accessTokens.put(principal.getName(), accessToken);
    }

    private Twitter authenticate(Principal principal) throws TwitterException, IOException {
        RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken accessToken = null;
        Scanner sc = new Scanner(System.in);
        while (null == accessToken) {
            System.out.println("Open the following URL and grant access to your account: ");
            System.out.println(requestToken.getAuthorizationURL());
            System.out.print("Enter the PIN (if available) or just hit enter.[PIN]: ");
            String pin = sc.nextLine();
            try {
                if (pin.length() > 0) {
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                } else {
                    accessToken = twitter.getOAuthAccessToken();
                }
            } catch (TwitterException te) {
                if (401 == te.getStatusCode()) {
                    System.out.println("Unable to get the access token.");
                } else {
                    te.printStackTrace();
                }
            }
        }
        accessTokens.put(principal.getName(), accessToken);
        return twitter;
    }
}
