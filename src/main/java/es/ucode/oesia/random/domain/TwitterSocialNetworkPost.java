package es.ucode.oesia.random.domain;

import twitter4j.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwitterSocialNetworkPost extends SocialNetworkPost {

    public TwitterSocialNetworkPost(Status tweet) {
        super.setText(tweet.getText());
        List<String> tags = Arrays.asList(tweet.getText().split("#[^ ]+"));
        super.setTags(tags);
        List<SocialNetworkUser> taggedUsers = new ArrayList<>();
        for (String u : tweet.getText().split("@[^ ]+")) {
            SocialNetworkUser user = new TwitterSocialNetworkUser(u);
            taggedUsers.add(user);
        }
        super.setTaggedUsers(taggedUsers);
        super.setDate(tweet.getCreatedAt());
        super.setCreator(new TwitterSocialNetworkUser(tweet.getUser()));
    }
}
