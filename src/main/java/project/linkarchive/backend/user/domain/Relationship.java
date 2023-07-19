package project.linkarchive.backend.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Relationship extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id")
    private Long id;
    private Long follower;
    private Long followee;

    @Builder
    public Relationship(Long follower, Long followee) {
        this.follower = follower;
        this.followee = followee;
    }

    public static Relationship build(Long followerId, Long followeeId) {
        return Relationship.builder()
                .follower(followerId)
                .followee(followeeId)
                .build();
    }
}
