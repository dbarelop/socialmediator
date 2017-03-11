package es.ucode.oesia.random.repository;

import es.ucode.oesia.random.domain.SocialNetwork;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserSocialNetworksRepository {
    private Map<String, Map<SocialNetwork, String>> authTokens = new HashMap<>();

    public void addSocialNetwork(String user, SocialNetwork socialNetwork, String authToken) {
        if (authTokens.containsKey(user)) {
            authTokens.get(user).put(socialNetwork, authToken);
        } else {
            Map<SocialNetwork, String> authMap = new HashMap<>();
            authMap.put(socialNetwork, authToken);
            authTokens.put(user, authMap);
        }
    }

    public Map<SocialNetwork,String> findByUser(String user) {
        return authTokens.get(user);
    }
}
