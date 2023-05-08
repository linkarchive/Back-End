package project.linkarchive.backend.user.domain;

import lombok.*;
import project.linkarchive.backend.advice.entityBase.TimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_profile_image")
public class UserProfileImage extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_image_id")
    private Long id;

    @Column(name = "user_profile_img")
    private String userProfileImage;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserProfileImage(Long id, String userProfileImage, User user) {
        this.id = id;
        this.userProfileImage = userProfileImage;
        this.user = user;
    }



}