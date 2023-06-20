package project.linkarchive.backend.auth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoEmail {

    public String email;

    public KakaoEmail(String email){
        this.email = email;
    }
}
