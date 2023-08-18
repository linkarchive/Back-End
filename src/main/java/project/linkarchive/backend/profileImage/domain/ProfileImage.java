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

    private String fileName;

    @OneToOne(mappedBy = "profileImage", fetch = FetchType.LAZY)
    private User user;

    @Builder
    public ProfileImage(Long id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public static ProfileImage create(String fileName) {
        return ProfileImage.builder()
                .fileName(fileName)
                .build();
    }

    public void updateProfileImage(String profileImage) {
        this.fileName = profileImage;
    }

}