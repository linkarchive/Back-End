package project.linkarchive.backend.user.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImageResponse {

    private String profileImageFileName;

    public ProfileImageResponse(String profileImageFileName) {
        this.profileImageFileName = profileImageFileName;
    }

}