package project.linkarchive.backend.profileImage.response;

import lombok.Getter;

@Getter
public class ProfileImageResponse {

    private String profileImage;

    public ProfileImageResponse(String profileImage) {
        this.profileImage = profileImage;
    }

}