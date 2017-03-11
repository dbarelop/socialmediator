package es.ucode.oesia.random.domain;

import facebook4j.Post;

public class FacebookSocialNetworkPost extends SocialNetworkPost {

    public FacebookSocialNetworkPost(Post post) {
        // TODO: complete
        //super.setCreator(...);
        super.setDate(post.getCreatedTime());
        //super.setTaggedUsers(...);
        super.setTags(post.getMessage() != null ? getTags(post.getMessage()) : null);
        super.setText(post.getMessage());
        //super.setUrl(???);
    }
}
