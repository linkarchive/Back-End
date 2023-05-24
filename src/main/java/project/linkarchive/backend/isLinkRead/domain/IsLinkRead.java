package project.linkarchive.backend.isLinkRead.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.CreatedEntity;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.user.domain.User;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IsLinkRead extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "is_link_read_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "link_id")
    private Link link;

    @Builder
    public IsLinkRead(User user, Link link) {
        this.user = user;
        this.link = link;
    }

    public static IsLinkRead build(User user, Link link) {
        return IsLinkRead.builder()
                .user(user)
                .link(link)
                .build();
    }

}