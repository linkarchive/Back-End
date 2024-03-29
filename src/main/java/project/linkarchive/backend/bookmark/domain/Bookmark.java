package project.linkarchive.backend.bookmark.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.CreatedEntity;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.pin.enums.PinStatus;
import project.linkarchive.backend.user.domain.User;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_mark_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PinStatus pinStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "link_id")
    private Link link;

    @Builder
    public Bookmark(Long id, PinStatus pinStatus, User user, Link link) {
        this.id = id;
        this.pinStatus = pinStatus.UNFIXED;
        this.user = user;
        this.link = link;
    }

    public static Bookmark create(User user, Link link) {
        return Bookmark.builder()
                .pinStatus(PinStatus.UNFIXED)
                .user(user)
                .link(link)
                .build();
    }

}