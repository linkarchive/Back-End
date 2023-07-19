package project.linkarchive.backend.relationship.domain;

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
    private Long followee;
    private Long follower;


    @Builder
    public Relationship(Long id, Long followee, Long follower) {
        this.id = id;
        this.followee = followee;
        this.follower = follower;
    }

    public static Relationship create(Long followeeId, Long followerId) {
        return Relationship.builder()
                .follower(followeeId)
                .followee(followerId)
                .build();
    }
}
