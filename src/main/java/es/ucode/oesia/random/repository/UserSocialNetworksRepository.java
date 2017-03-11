package es.ucode.oesia.random.repository;

import es.ucode.oesia.random.domain.SocialNetwork;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserSocialNetworksRepository {
    private Map<String, Map<SocialNetwork, Object>> authTokens = new HashMap<>();

    public void addSocialNetwork(String user, SocialNetwork socialNetwork, Object authToken) {
        if (authTokens.containsKey(user)) {
            authTokens.get(user).put(socialNetwork, authToken);
        } else {
            Map<SocialNetwork, Object> authMap = new HashMap<>();
            authMap.put(socialNetwork, authToken);
            authTokens.put(user, authMap);
        }
    }

    public Map<SocialNetwork,Object> findByUser(String user) {
        return authTokens.get(user);
    }

    public Object findByUserAndSocialNetwork(String user, SocialNetwork socialNetwork) {
        return authTokens.containsKey(user) ? authTokens.get(user).get(socialNetwork) : null;
    }
}
