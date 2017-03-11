package es.ucode.oesia.random.domain;

import facebook4j.Post;

public class FacebookSocialNetworkPost extends SocialNetworkPost {

    public FacebookSocialNetworkPost(Post post) {
        super.setText(post.getMessage());
        // TODO: complete
    }
}
