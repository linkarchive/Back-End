package project.linkarchive.backend.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;
import project.linkarchive.backend.bookmark.domain.BookMark;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "social_id")
    private Long socialId;
    @Column(name = "email")
    private String email;

    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_introduce")
    private String introduce;

    @Column(name = "user_role")
    private String userRole;


    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserProfileImage userProfileImage;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RefreshToken refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserHashTag> userHashTagList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BookMark> bookMarkList = new ArrayList<>();

    @Builder
    public User(Long socialId, String email, String userName, String introduce, String userRole, RefreshToken refreshToken, UserProfileImage userProfileImage, List<UserHashTag> userHashTagList, List<BookMark> bookMarkList) {
        this.socialId = socialId;
        this.email = email;
        this.userName = userName;
        this.introduce = introduce;
        this.userRole = userRole;
        this.refreshToken = refreshToken;
        this.userProfileImage = userProfileImage;
        this.userHashTagList = userHashTagList;
        this.bookMarkList = bookMarkList;
    }

}