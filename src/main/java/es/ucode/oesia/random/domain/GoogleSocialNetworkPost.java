package es.ucode.oesia.random.domain;

import com.google.api.services.plus.model.Activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class GoogleSocialNetworkPost extends SocialNetworkPost {

    public GoogleSocialNetworkPost(Activity activity) {
        try {
            super.setCreator(new GoogleSocialNetworkUser(activity.getActor()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.setDate(new Date(activity.getPublished().getValue()));
        super.setTags(getTags(activity.getTitle()));
        super.setText(activity.getTitle());
        try {
            super.setUrl(new URL(activity.getUrl()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
