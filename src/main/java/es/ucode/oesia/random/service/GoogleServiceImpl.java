package es.ucode.oesia.random.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.plus.model.Person;
import es.ucode.oesia.random.domain.SocialNetwork;
import es.ucode.oesia.random.domain.SocialNetworkPost;
import es.ucode.oesia.random.repository.UserSocialNetworksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoogleServiceImpl implements SocialNetworkService {

    private Map<String, GoogleAuthorizationCodeFlow> authorizationCodeFlows = new HashMap<>();

    @Autowired
    private UserSocialNetworksRepository userSocialNetworksRepository;

    private String callbackUrl;

    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Override
    public List<SocialNetworkPost> getLatestPosts(Principal principal) {
        Credential credential = (Credential) userSocialNetworksRepository.findByUserAndSocialNetwork(principal.getName(), SocialNetwork.google);
        try {
            if (credential != null) {
                Plus plus = new Plus.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential).setApplicationName("socialmediator").build();
                // TODO: this retrieves the user's posts, but not their friends'
                // Possible solution: iterate friends list and get public collections
                plus.activities().list("me", "public");
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isAuthorized(Principal principal) {
        return userSocialNetworksRepository.findByUserAndSocialNetwork(principal.getName(), SocialNetwork.google) != null;
    }

    public String getAuthorizationURL(Principal principal, String requestURL) throws GeneralSecurityException, IOException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(new ClassPathResource("google_client_id.json").getInputStream()));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, Collections.singleton(PlusScopes.PLUS_ME)).build();
        String url = flow.newAuthorizationUrl().setRedirectUri(requestURL + "/callback").build();
        authorizationCodeFlows.put(principal.getName(), flow);

        // TODO: workaround
        callbackUrl = requestURL + "/callback";

        return url;
    }

    public void authorize(Principal principal, String authorizationCode) throws IOException, GeneralSecurityException {
        GoogleAuthorizationCodeFlow flow = authorizationCodeFlows.get(principal.getName());
        GoogleAuthorizationCodeTokenRequest tokenRequest = flow.newTokenRequest(authorizationCode);
        tokenRequest.setRedirectUri(callbackUrl);
        GoogleTokenResponse tokenResponse = tokenRequest.execute();

        Credential credential = flow.createAndStoreCredential(tokenResponse, principal.getName());

        userSocialNetworksRepository.addSocialNetwork(principal.getName(), SocialNetwork.google, credential);
    }
}
