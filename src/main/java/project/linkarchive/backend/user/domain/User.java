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
public class User extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String socialId;
    private String email;
    private String name;
    private String nickName;
    private String introduce;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RefreshToken refreshToken;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserProfileImage userProfileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserHashTag> userHashTagList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BookMark> bookMarkList = new ArrayList<>();

    @Builder
    public User(Long id, String socialId, String email, String name, String nickName, String introduce, RefreshToken refreshToken, UserProfileImage userProfileImage, List<UserHashTag> userHashTagList, List<BookMark> bookMarkList) {
        this.id = id;
        this.socialId = socialId;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.introduce = introduce;
        this.refreshToken = refreshToken;
        this.userProfileImage = userProfileImage;
        this.userHashTagList = userHashTagList;
        this.bookMarkList = bookMarkList;
    }

}