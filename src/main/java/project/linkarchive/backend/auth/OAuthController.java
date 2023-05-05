package project.linkarchive.backend.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthController {

    @ResponseBody
    @GetMapping("/auth/kakao")
    public String kakaoCallback(@RequestParam String code){
        System.out.println("code = " + code);
        return code;
    }



}
