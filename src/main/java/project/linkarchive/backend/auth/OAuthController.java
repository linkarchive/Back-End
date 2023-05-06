package project.linkarchive.backend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OAuthController {

   private final OAuthService oAuthService;

    @GetMapping("/auth/kakao")
    public UserEntity kakaoCallback(@RequestParam String code){
        System.out.println("code = " + code);
        OauthToken oauthToken = oAuthService.getAccessToken(code);

        UserEntity user = oAuthService.saveUser(oauthToken.getAccess_token());

        return user;
    }


}
