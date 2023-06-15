package project.linkarchive.backend.profileImage.response;

import lombok.Getter;

@Getter
public class ProfileImageResponse {

    private String profileImageFileName;

    public ProfileImageResponse(String profileImageFileName) {
        this.profileImageFileName = profileImageFileName;
    }

}