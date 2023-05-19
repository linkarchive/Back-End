package project.linkarchive.backend.user.domain;

import lombok.*;
import project.linkarchive.backend.advice.entityBase.TimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_image_id")
    private Long id;

    @Column(name = "profile_image")
    private String profileImage;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public ProfileImage(Long id, String profileImage, User user) {
        this.id = id;
        this.profileImage = profileImage;
        this.user = user;
    }

}