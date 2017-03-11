package es.ucode.oesia.random.service;

import es.ucode.oesia.random.domain.SocialNetworkPost;

/**
 * Clase FilterByLucene
 */
public class FilterByLucene implements FilterService{

    @Override
    public boolean filter(SocialNetworkPost post, String parameter) {
        new StandardAnalyzer();
        return true;
    }

}
