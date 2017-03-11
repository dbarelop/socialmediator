
package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkPost;
import java.util.List;
/**
 * Clase FilterService
 */
public interface FilterService {

    boolean filter(SocialNetworkPost post, String parameter);
    
    void filterAll(List<SocialNetworkPost> posts, String parameter);
    
}
