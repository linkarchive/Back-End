package project.linkarchive.backend.relationship.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Relationship extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee_id")
    @JsonIgnore
    private User followee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    @JsonIgnore
    private User follower;

    @Builder
    public Relationship(Long id, User followee, User follower) {
        this.id = id;
        this.followee = followee;
        this.follower = follower;
    }

    public static Relationship create(User followee, User follower) {
        return Relationship.builder()
                .followee(followee)
                .follower(follower)
                .build();
    }

}
