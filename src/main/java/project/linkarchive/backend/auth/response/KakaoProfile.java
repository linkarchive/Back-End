package project.linkarchive.backend.auth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfile {

    public String id;

    @JsonSetter("kakao_account")
    public KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class KakaoAccount {
        @JsonSetter("profile")
        public Profile profile;

        @JsonSetter("email")
        public String email;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Profile {
            @JsonSetter("nickname")
            public String nickname;

            @JsonSetter("profile_image_url")
            public String profileImageUrl;
        }
    }

}