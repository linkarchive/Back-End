package project.linkarchive.backend.hashtag.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.CreatedEntity;
import project.linkarchive.backend.user.domain.User;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHashtag extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_hashtag_id")
    private Long id;

    private int usageCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @Builder
    public UserHashtag(int usageCount, User user, Hashtag hashtag) {
        this.usageCount = usageCount;
        this.user = user;
        this.hashtag = hashtag;
    }

    public static UserHashtag create(int usageCount, User user, Hashtag hashTag) {
        return UserHashtag.builder()
                .usageCount(usageCount)
                .user(user)
                .hashtag(hashTag)
                .build();
    }

}