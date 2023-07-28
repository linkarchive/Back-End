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

    @OneToOne(mappedBy = "profileImage", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @Builder
    public ProfileImage(Long id, String profileImageFilename) {
        this.id = id;
        this.profileImageFilename = profileImageFilename;
    }

    public static ProfileImage create(String profileImageFilename) {
        return ProfileImage.builder()
                .profileImageFilename(profileImageFilename)
                .build();
    }

    public void updateProfileImage(String profileImage) {
        this.profileImageFilename = profileImage;
    }

}