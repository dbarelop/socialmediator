package es.ucode.oesia.random.domain;

import java.net.URL;

public abstract class SocialNetworkUser {

    private String username;
    private String name;
    private String email;
    private URL picture;
    private URL profileURL;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public URL getPicture() {
        return picture;
    }

    public void setPicture(URL picture) {
        this.picture = picture;
    }

    public URL getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(URL profileURL) {
        this.profileURL = profileURL;
    }
}
