package project.linkarchive.backend.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.link.domain.Link;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Relationship extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;
    private Long followerId;
    private Long followingId;

    @Builder
    public Relationship(Long followerId, Long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }

    public static Relationship build(Long followerId, Long followingId) {
        return Relationship.builder()
                .followerId(followerId)
                .followingId(followingId)
                .build();
    }
}
