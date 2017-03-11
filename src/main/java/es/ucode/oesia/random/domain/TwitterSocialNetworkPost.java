package es.ucode.oesia.random.domain;

import twitter4j.HashtagEntity;
import twitter4j.Status;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class TwitterSocialNetworkPost extends SocialNetworkPost {

    public TwitterSocialNetworkPost(Status tweet) {
        super.setText(getTweetText(tweet));
        super.setTags(getTags(tweet));
        super.setTaggedUsers(getTaggedUsers(tweet));
        super.setDate(tweet.getCreatedAt());
        super.setCreator(new TwitterSocialNetworkUser(tweet.getUser()));
    }

    private List<SocialNetworkUser> getTaggedUsers(Status tweet) {
        return Arrays.stream(tweet.getUserMentionEntities()).map(taggedUser -> new TwitterSocialNetworkUser(taggedUser.getScreenName())).collect(Collectors.toList());
    }

    private List<String> getTags(Status tweet) {
        return Arrays.stream(tweet.getHashtagEntities()).map(HashtagEntity::getText).collect(Collectors.toList());
    }

    private String getTweetText(Status tweet){
        if (isRetweet(tweet)){
            return tweet.getRetweetedStatus().getText();
        }else {
            return tweet.getText();
        }
    }

    private boolean isRetweet(Status tweet){
        return tweet.getRetweetedStatus() != null;
    }


}
