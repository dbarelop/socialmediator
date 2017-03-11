package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetwork;
import es.ucode.oesia.random.domain.SocialNetworkPost;
import es.ucode.oesia.random.repository.UserSocialNetworksRepository;
import facebook4j.*;
import facebook4j.auth.AccessToken;
import facebook4j.auth.OAuthAuthorization;
import facebook4j.auth.OAuthSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class FacebookServiceImpl implements SocialNetworkService {

    @Autowired
    private UserSocialNetworksRepository userSocialNetworksRepository;

    @Override
    public List<SocialNetworkPost> getLatestPosts(Principal principal) {
        Facebook fb = FacebookFactory.getSingleton();
        try {
            AccessToken accessToken = (AccessToken) userSocialNetworksRepository.findByUserAndSocialNetwork(principal.getName(), SocialNetwork.facebook);
            if (accessToken != null) {
                fb.setOAuthAccessToken(accessToken);
                ResponseList<Post> posts = fb.getFeed();
                for (Post p : posts) {
                    System.out.println(p.getMessage());
                }
                // TODO: complete
            }
        } catch (FacebookException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isAuthorized(Principal principal) {
        return userSocialNetworksRepository.findByUserAndSocialNetwork(principal.getName(), SocialNetwork.facebook) != null;
    }

    public String getAuthorizationURL(String requestURL) {
        Facebook fb = FacebookFactory.getSingleton();
        return fb.getOAuthAuthorizationURL(requestURL + "/callback");
    }

    public void authorize(Principal principal, String oAuthCode) throws FacebookException {
        Facebook fb = FacebookFactory.getSingleton();
        AccessToken accessToken = fb.getOAuthAccessToken(oAuthCode);
        userSocialNetworksRepository.addSocialNetwork(principal.getName(), SocialNetwork.facebook, accessToken);
    }
}
