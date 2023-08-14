package project.linkarchive.backend.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;
import project.linkarchive.backend.auth.AuthProvider;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.bookmark.domain.Bookmark;
import project.linkarchive.backend.hashtag.domain.UserHashtag;
import project.linkarchive.backend.isLinkRead.domain.IsLinkRead;
import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.user.request.UpdateNicknameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static project.linkarchive.backend.advice.data.DataConstants.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`user`")
public class User extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String socialId;
    private String email;
    private String nickname;
    private String introduce;
    private int followerCount;
    private int followingCount;
    private int linkCount;
    private int bookmarkCount;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image_id")
    private ProfileImage profileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserHashtag> userHashtagList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<IsLinkRead> isLinkReadList = new ArrayList<>();

    @Builder
    public User(Long id, AuthProvider authProvider, String socialId, String email, String nickname, String introduce, int followerCount, int followingCount, int linkCount, int bookmarkCount, ProfileImage profileImage) {
        this.id = id;
        this.authProvider = authProvider;
        this.socialId = socialId;
        this.email = email;
        this.nickname = nickname;
        this.introduce = introduce;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.linkCount = linkCount;
        this.bookmarkCount = bookmarkCount;
        this.profileImage = profileImage;
    }

    public static User localCreate(ProfileImage profileImage) {
        return User.builder()
                .authProvider(AuthProvider.LOCAL)
                .socialId(SOCIAL_LOGIN)
                .nickname(EMPTY)
                .email(LOCAL_EMAIL)
                .introduce(EMPTY)
                .followerCount(DEFAULT_COUNT)
                .followingCount(DEFAULT_COUNT)
                .linkCount(DEFAULT_COUNT)
                .bookmarkCount(DEFAULT_COUNT)
                .profileImage(profileImage)
                .build();
    }

    public static User create(AuthProvider authProvider, KakaoProfile kakaoProfile, ProfileImage profileImage) {
        return User.builder()
                .authProvider(authProvider)
                .socialId(kakaoProfile.getId())
                .nickname(EMPTY)
                .email(kakaoProfile.getKakaoAccount().getEmail())
                .introduce(EMPTY)
                .followerCount(DEFAULT_COUNT)
                .followingCount(DEFAULT_COUNT)
                .linkCount(DEFAULT_COUNT)
                .bookmarkCount(DEFAULT_COUNT)
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