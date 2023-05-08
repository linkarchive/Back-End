package project.linkarchive.backend.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfile {

    public Long id;
    @JsonSetter("connected_at")
    public String connectedAt;
    public Properties properties;
    @JsonSetter("kakao_account")
    public KakaoAccount kakaoAccount;


    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Properties {
        @JsonSetter("nickname")
        public String nickname;

        @JsonSetter("profile_image")
        public String profileImage;
        @JsonSetter("thumbnail_image")
        public String thumbnailImage;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class KakaoAccount {
        @JsonSetter("profile_nickname_needs_agreement")
        public Boolean profileNicknameNeedsAgreement;
        @JsonSetter("profile_image_needs_agreement")
        public Boolean profileImageNeedsAgreement;
        @JsonSetter("profile")
        public Profile profile;
        @JsonSetter("has_email")
        public Boolean hasEmail;
        @JsonSetter("email_needs_agreement")
        public Boolean emailNeedsAgreement;

        @JsonSetter("is_email_valid")
        public Boolean isEmailValid;
        @JsonSetter("is_email_verified")
        public Boolean isEmailVerified;
        @JsonSetter("email")
        public String email;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Profile {
            @JsonSetter("nickname")
            public String nickname;
            @JsonSetter("thumbnail_image_url")
            public String thumbnailImageUrl;
            @JsonSetter("profile_image_url")
            public String profileImageUrl;
            @JsonSetter("is_default_image")
            public Boolean isDefaultImage;
        }
    }

}