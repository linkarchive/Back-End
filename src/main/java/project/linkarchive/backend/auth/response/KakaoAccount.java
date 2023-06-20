package project.linkarchive.backend.auth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoAccount {

    public String email;

    public KakaoAccount(String email){
        this.email = email;
    }
}
