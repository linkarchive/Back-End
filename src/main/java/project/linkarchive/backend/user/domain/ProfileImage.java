package project.linkarchive.backend.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;
import project.linkarchive.backend.auth.response.KakaoProfile;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_image_id")
    private Long id;

    private String profileImageFilename;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public ProfileImage(Long id, String profileImageFilename, User user) {
        this.id = id;
        this.profileImageFilename = profileImageFilename;
        this.user = user;
    }

    public static ProfileImage build(KakaoProfile kakaoProfile, User user) {
        return ProfileImage.builder()
                .profileImageFilename(kakaoProfile.getKakaoAccount().getProfile().getProfileImageUrl())
                .user(user)
                .build();
    }

    public void updateProfileImage(String profileImage) {
        this.profileImageFilename = profileImage;
    }

}