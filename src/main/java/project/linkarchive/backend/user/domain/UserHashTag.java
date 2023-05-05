package project.linkarchive.backend.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.CreatedEntity;
import project.linkarchive.backend.hashtag.domain.HashTag;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHashTag extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_hashtag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hashtag_id")
    private HashTag hashTag;

    @Builder
    public UserHashTag(Long id, User user, HashTag hashTag) {
        this.id = id;
        this.user = user;
        this.hashTag = hashTag;
    }

}