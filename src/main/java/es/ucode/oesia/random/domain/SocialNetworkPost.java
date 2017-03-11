package es.ucode.oesia.random.domain;

import java.net.URL;
import java.util.Date;
import java.util.List;

public abstract class SocialNetworkPost {
    private String text;
    private List<String> tags;
    private List<SocialNetworkUser> taggedUsers;
    private Date date;
    private URL url;
    private SocialNetworkUser creator;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<SocialNetworkUser> getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(List<SocialNetworkUser> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public SocialNetworkUser getCreator() {
        return creator;
    }

    public void setCreator(SocialNetworkUser creator) {
        this.creator = creator;
    }
}
