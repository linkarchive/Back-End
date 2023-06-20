package project.linkarchive.backend.auth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfile {

    public String id;

    @JsonProperty("kakao_account")
    public KakaoEmail kakaoEmail;

    public KakaoProfile(String id, KakaoEmail kakaoEmail) {
        this.id = id;
        this.kakaoEmail = kakaoEmail;
    }

}