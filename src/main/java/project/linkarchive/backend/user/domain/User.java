package project.linkarchive.backend.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;
import project.linkarchive.backend.auth.AuthProvider;
import project.linkarchive.backend.auth.domain.RefreshToken;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.bookmark.domain.Bookmark;
import project.linkarchive.backend.hashtag.domain.UserHashtag;
import project.linkarchive.backend.isLinkRead.domain.IsLinkRead;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.notification.domain.Notification;
import project.linkarchive.backend.notification.enums.NotificationPreference;
import project.linkarchive.backend.pin.domain.Pin;
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

    private String socialId;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String email;
    private String nickname;
    private String introduce;
    private int followerCount;
    private int followingCount;
    private int linkCount;
    private int bookmarkCount;

    @Enumerated(EnumType.STRING)
    private NotificationPreference notificationPreference;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "profile_image_id")
    private ProfileImage profileImage;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "pin_id")
    private Pin pin;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<RefreshToken> refreshToken = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Link> linkList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserHashtag> userHashtagList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<IsLinkRead> isLinkReadList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Notification> notificationList = new ArrayList<>();

    @Builder
    public User(Long id, String socialId, AuthProvider authProvider, String email, String nickname, String introduce, int followerCount, int followingCount, int linkCount, int bookmarkCount, NotificationPreference notificationPreference, ProfileImage profileImage, Pin pin) {
        this.id = id;
        this.socialId = socialId;
        this.authProvider = authProvider;
        this.email = email;
        this.nickname = nickname;
        this.introduce = introduce;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.linkCount = linkCount;
        this.bookmarkCount = bookmarkCount;
        this.notificationPreference = notificationPreference;
        this.profileImage = profileImage;
        this.pin = pin;
    }

    public static User localCreate(ProfileImage profileImage, Pin pin) {
        return User.builder()
                .socialId(SOCIAL_LOGIN)
                .authProvider(AuthProvider.LOCAL)
                .email(LOCAL_EMAIL)
                .nickname(EMPTY)
                .introduce(EMPTY)
                .followerCount(DEFAULT_COUNT)
                .followingCount(DEFAULT_COUNT)
                .linkCount(DEFAULT_COUNT)
                .bookmarkCount(DEFAULT_COUNT)
                .notificationPreference(NotificationPreference.ALLOWED)
                .profileImage(profileImage)
                .pin(pin)
                .build();
    }

    public static User create(AuthProvider authProvider, KakaoProfile kakaoProfile, ProfileImage profileImage, Pin pin) {
        return User.builder()
                .socialId(kakaoProfile.getId())
                .authProvider(authProvider)
                .email(kakaoProfile.getKakaoAccount().getEmail())
                .nickname(EMPTY)
                .introduce(EMPTY)
                .followerCount(DEFAULT_COUNT)
                .followingCount(DEFAULT_COUNT)
                .linkCount(DEFAULT_COUNT)
                .bookmarkCount(DEFAULT_COUNT)
                .notificationPreference(NotificationPreference.ALLOWED)
                .profileImage(profileImage)
                .pin(pin)
                .build();
    }

    public void updateNickName(UpdateNicknameRequest request) {
        this.nickname = request.getNickname();
    }

    public void updateProfile(UpdateProfileRequest request) {
        this.nickname = request.getNickname();
        this.introduce = request.getIntroduce();
    }

    public void updateAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

}