package project.linkarchive.backend.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.advice.success.SuccessCodeConst;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.auth.AuthCodeRequest;
import project.linkarchive.backend.auth.OauthToken;
import project.linkarchive.backend.auth.service.OAuthService;
import project.linkarchive.backend.jwt.JwtProperties;

@RestController
@RequiredArgsConstructor
public class OAuthController {

   private final OAuthService oAuthService;

    @PostMapping("/auth/kakao")
    public ResponseEntity getLogin(
            @RequestBody AuthCodeRequest authCodeRequest
    ){
        OauthToken oauthToken = oAuthService.getAccessToken(authCodeRequest.getCode());

        String jwtToken = oAuthService.saveUserAndGetToken(oauthToken.getAccess_token());

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(SuccessCodeConst.LOGIN_SUCCESS));
    }



}
