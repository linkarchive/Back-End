package project.linkarchive.backend.security;

import org.springframework.security.core.Authentication;
import project.linkarchive.backend.auth.service.OAuthService;

import java.util.Collections;
import java.util.List;

public class TokenAuthentication implements Authentication {
    private boolean isAuthenticated = false;
    private String token;
    private Long id;
    public TokenAuthentication(String token, Long id){
        this.token = token;
        this.id = id;
    }

    @Override
    public List getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Long getPrincipal() {
        return id;
    }


    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return "";
    }
}
