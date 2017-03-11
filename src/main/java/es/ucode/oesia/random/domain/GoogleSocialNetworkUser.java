package es.ucode.oesia.random.domain;

import com.google.api.services.plus.model.Activity;

import java.io.IOException;
import java.net.URL;

public class GoogleSocialNetworkUser extends SocialNetworkUser {

    public GoogleSocialNetworkUser(Activity.Actor actor) throws IOException {
        super.setName(actor.getName() != null ? actor.getName().toPrettyString() : null);
        super.setPicture(new URL(actor.getImage().getUrl()));
        super.setUsername(actor.getDisplayName());
    }
}
