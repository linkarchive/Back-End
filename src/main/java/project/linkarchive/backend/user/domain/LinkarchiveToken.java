package project.linkarchive.backend.user.domain;

import lombok.*;
import project.linkarchive.backend.advice.entityBase.CreatedEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LinkarchiveToken extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "linkarchive_token_id")
    private Long id;

    @Column(length = 500)
    private String accessToken;

    @Column(length = 500)
    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public LinkarchiveToken(Long id,String accessToken, String refreshToken, User user) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

}