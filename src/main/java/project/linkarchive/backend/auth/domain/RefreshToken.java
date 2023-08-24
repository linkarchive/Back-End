package project.linkarchive.backend.auth.domain;

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
public class RefreshToken extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    @Column(length = 500)
    private String refreshToken;

    private String agent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public RefreshToken(Long id, String refreshToken, String agent, User user) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.agent = agent;
        this.user = user;
    }

    public static RefreshToken create(String refreshToken, String userAgent, User user) {
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