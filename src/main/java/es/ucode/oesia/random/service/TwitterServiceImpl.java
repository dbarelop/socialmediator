package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetwork;
import es.ucode.oesia.random.domain.SocialNetworkPost;
import es.ucode.oesia.random.domain.TwitterSocialNetworkPost;
import es.ucode.oesia.random.repository.UserSocialNetworksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TwitterServiceImpl implements SocialNetworkService {

    @Autowired
    private UserSocialNetworksRepository userSocialNetworksRepository;

    @Override
    public List<SocialNetworkPost> getLatestPosts(Principal principal) {
        Twitter twitter = TwitterFactory.getSingleton();
        try {
            AccessToken accessToken = (AccessToken) userSocialNetworksRepository.findByUserAndSocialNetwork(principal.getName(), SocialNetwork.twitter);
            if (accessToken != null) {
                twitter.setOAuthAccessToken(accessToken);
                List<Status> statuses = twitter.getHomeTimeline();
                return statuses.stream().map(TwitterSocialNetworkPost::new).collect(Collectors.toList());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isAuthorized(Principal principal) {
        return userSocialNetworksRepository.findByUserAndSocialNetwork(principal.getName(), SocialNetwork.twitter) != null;
    }

    public RequestToken getRequestToken() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        RequestToken requestToken = twitter.getOAuthRequestToken();
        return requestToken;
    }

    public void authorize(Principal principal, String oauthVerifier) throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        AccessToken accessToken = twitter.getOAuthAccessToken(oauthVerifier);
        userSocialNetworksRepository.addSocialNetwork(principal.getName(), SocialNetwork.twitter, accessToken);
    }

}
