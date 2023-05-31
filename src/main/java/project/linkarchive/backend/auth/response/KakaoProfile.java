package project.linkarchive.backend.auth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfile {

    public String id;

    @JsonSetter("kakao_account")
    public KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class KakaoAccount {

        @JsonSetter("email")
        public String email;

    }

    public KakaoProfile(String id, String email) {
        this.id = id;
        this.kakaoAccount = new KakaoAccount();
        this.kakaoAccount.email = email;
    }

}