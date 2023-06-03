package project.linkarchive.backend.auth.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.CreatedEntity;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.request.UpdateNicknameRequest;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    @Column(length = 500)
    private String refreshToken;

    private String agent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public RefreshToken(String refreshToken, String agent, User user) {
        this.refreshToken = refreshToken;
        this.agent = agent;
        this.user = user;
    }

    public static RefreshToken build(String refreshToken, String userAgent, User user) {
        return RefreshToken.builder()
                .refreshToken(refreshToken)
                .agent(userAgent)
                .user(user)
                .build();
    }

    public void updateRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken.getRefreshToken();
    }

}