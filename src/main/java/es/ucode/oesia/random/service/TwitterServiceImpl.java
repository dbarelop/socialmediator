package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetwork;
import es.ucode.oesia.random.domain.SocialNetworkPost;
import es.ucode.oesia.random.domain.TwitterSocialNetworkPost;
import es.ucode.oesia.random.repository.UserSocialNetworksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.security.Principal;
import java.util.Arrays;
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
                List<Status> statuses = twitter.getHomeTimeline(new Paging(1, SocialNetworkAggregatorService.PAGE_SIZE));
                return statuses.stream().map(TwitterSocialNetworkPost::new).collect(Collectors.toList());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SocialNetworkPost> getPosts(Principal principal, int page) {
        Twitter twitter = TwitterFactory.getSingleton();
        try {
            AccessToken accessToken = (AccessToken) userSocialNetworksRepository.findByUserAndSocialNetwork(principal.getName(), SocialNetwork.twitter);
            if (accessToken != null) {
                twitter.setOAuthAccessToken(accessToken);
                List<Status> statuses = twitter.getHomeTimeline(new Paging(page, SocialNetworkAggregatorService.PAGE_SIZE));
                return statuses.stream().map(TwitterSocialNetworkPost::new).collect(Collectors.toList());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SocialNetworkPost> getLatestPostsByTag(Principal principal, String tag) {
        Twitter twitter = TwitterFactory.getSingleton();
        try {
            AccessToken accessToken = (AccessToken) userSocialNetworksRepository.findByUserAndSocialNetwork(principal.getName(), SocialNetwork.twitter);
            if (accessToken != null) {
                twitter.setOAuthAccessToken(accessToken);
                List<Status> statuses = twitter.getHomeTimeline(new Paging(1, SocialNetworkAggregatorService.PAGE_SIZE));
                return statuses.stream().filter(t -> Arrays.stream(t.getHashtagEntities()).anyMatch(h -> h.getText().equals(tag)))
                        .map(TwitterSocialNetworkPost::new).collect(Collectors.toList());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isAuthorized(Principal principal) {
        return userSocialNetworksRepository.findByUserAndSocialNetwork(principal.getName(), SocialNetwork.twitter) != null;
    }

    public String getAuthorizationURL() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        RequestToken requestToken = twitter.getOAuthRequestToken();
        return requestToken.getAuthorizationURL();
    }

    public void authorize(Principal principal, String oauthVerifier) throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        AccessToken accessToken = twitter.getOAuthAccessToken(oauthVerifier);
        userSocialNetworksRepository.addSocialNetwork(principal.getName(), SocialNetwork.twitter, accessToken);
    }

}
