package project.linkarchive.backend.auth.response;

import lombok.Getter;

@Getter
public class OauthToken {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;

}