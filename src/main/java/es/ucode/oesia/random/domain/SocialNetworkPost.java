package es.ucode.oesia.random.domain;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    List<String> getTags(String text) {
        Pattern p = Pattern.compile("#(\\S+)");
        Matcher m = p.matcher(text);
        List<String> res = new ArrayList<>();
        while (m.find()) {
            res.add(m.group(1));
        }
        return res;
    }
}
