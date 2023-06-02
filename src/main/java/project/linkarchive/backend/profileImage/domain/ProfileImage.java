package project.linkarchive.backend.profileImage.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;
import project.linkarchive.backend.user.domain.User;

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
    public ProfileImage(String profileImageFilename, User user) {
        this.profileImageFilename = profileImageFilename;
        this.user = user;
    }

    public static ProfileImage build(String profileImageFilename, User user) {
        return ProfileImage.builder()
                .profileImageFilename(profileImageFilename)
                .user(user)
                .build();
    }

    public void updateProfileImage(String profileImage) {
        this.profileImageFilename = profileImage;
    }

}