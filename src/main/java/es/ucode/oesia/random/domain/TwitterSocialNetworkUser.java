package es.ucode.oesia.random.domain;

import twitter4j.User;

import java.net.MalformedURLException;
import java.net.URL;

public class TwitterSocialNetworkUser extends SocialNetworkUser {

    public TwitterSocialNetworkUser(String username) {
        super.setUsername(username);
    }

    public TwitterSocialNetworkUser(User user) {
        super.setUsername(user.getScreenName());
        super.setName(user.getName());
        super.setEmail(user.getEmail());
        try {
            super.setPicture(new URL(user.getProfileImageURL()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
