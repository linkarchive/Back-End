package project.linkarchive.backend.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.service.ProfileService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ProfileController {
    private final ProfileService profileService;


    @PostMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveImage(
            @RequestParam(value = "image") MultipartFile image,
            AuthInfo authInfo
    ) throws IOException {

        System.out.println("DiaryController.saveDiary");
        System.out.println(image);
        String url = profileService.saveProfileImage(image, authInfo.getId());

        return url;
    }

}






