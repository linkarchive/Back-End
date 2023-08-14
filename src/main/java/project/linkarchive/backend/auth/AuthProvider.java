package project.linkarchive.backend.auth;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AuthProvider {

    KAKAO("kakao"), GOOGLE("google"), NAVER("naver"), LOCAL("local");

    private String authProvider;

    AuthProvider(String authProvider) {
        this.authProvider = authProvider;
    }

    public static AuthProvider findByCode(String code) {
        return Arrays.stream(AuthProvider.values())
                .filter(provider -> provider.getAuthProvider().equals(code))
                .findFirst()
                .orElse(LOCAL);
    }

}