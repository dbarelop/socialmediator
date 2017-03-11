
package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkPost;
/**
 * Clase FilterService
 */
public interface FilterService {

    boolean filter(SocialNetworkPost post, String parameter);
    
}
