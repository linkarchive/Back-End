package project.linkarchive.backend.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.isLinkRead.domain.IsLinkRead;
import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.user.request.UpdateNicknameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;

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
    private String nickname;
    private String introduce;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProfileImage profileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserHashTag> userHashTagList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BookMark> bookMarkList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<IsLinkRead> isLinkReadList = new ArrayList<>();

    @Builder
    public User(String socialId, String email, String nickname, String introduce) {
        this.socialId = socialId;
        this.email = email;
        this.nickname = nickname;
        this.introduce = introduce;
    }

    public static User build(KakaoProfile kakaoProfile) {
        return User.builder()
                .socialId(kakaoProfile.id)
                .nickname("")
                .email(kakaoProfile.getKakaoAccount().getEmail())
                .introduce("")
                .build();
    }

    public void updateProfile(UpdateProfileRequest request) {
        this.nickname = request.getNickname();
        this.introduce = request.getIntroduce();
    }
    
    public void updateNickName(UpdateNicknameRequest request) {
        this.nickname = request.getNickname();
    }
    
}