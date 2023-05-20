package project.linkarchive.backend.security;

import lombok.Getter;

@Getter
public class AuthInfo {

    Long id;

    public AuthInfo(Long id) {
        this.id = id;
    }

}