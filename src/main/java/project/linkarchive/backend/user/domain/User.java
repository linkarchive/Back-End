package project.linkarchive.backend.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.hashtag.domain.UserHashTag;
import project.linkarchive.backend.isLinkRead.domain.IsLinkRead;
import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.user.request.UpdateNicknameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static project.linkarchive.backend.advice.data.DataConstants.DEFAULT_COUNT;
import static project.linkarchive.backend.advice.data.DataConstants.EMPTY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`user`", indexes = {@Index(name = "index_nickname", columnList = "nickname", unique = true)})
public class User extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String socialId;
    private String email;
    private String nickname;
    private String introduce;
    private int followerCount;
    private int followingCount;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image_id")
    private ProfileImage profileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserHashTag> userHashTagList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BookMark> bookMarkList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<IsLinkRead> isLinkReadList = new ArrayList<>();

    @Builder
    public User(Long id, String socialId, String email, String nickname, String introduce, int followerCount, int followingCount, ProfileImage profileImage) {
        this.id = id;
        this.socialId = socialId;
        this.email = email;
        this.nickname = nickname;
        this.introduce = introduce;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.profileImage = profileImage;
    }

    public static User create(KakaoProfile kakaoProfile, ProfileImage profileImage) {
        return User.builder()
                .socialId(kakaoProfile.getId())
                .nickname(EMPTY)
                .email(kakaoProfile.getKakaoAccount().getEmail())
                .introduce(EMPTY)
                .followerCount(DEFAULT_COUNT)
                .followingCount(DEFAULT_COUNT)
                .profileImage(profileImage)
                .build();
    }

    public void updateNickName(UpdateNicknameRequest request) {
        this.nickname = request.getNickname();
    }

    public void updateProfile(UpdateProfileRequest request) {
        this.nickname = request.getNickname();
        this.introduce = request.getIntroduce();
    }

}