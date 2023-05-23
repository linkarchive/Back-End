package project.linkarchive.backend.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.CreatedEntity;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.link.domain.LinkHashTag;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHashTag extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_hashtag_id")
    private Long id;

    private Long usageCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hashtag_id")
    private HashTag hashTag;

    @Builder
    public UserHashTag(Long usageCount, User user, HashTag hashTag) {
        this.usageCount = usageCount;
        this.user = user;
        this.hashTag = hashTag;
    }

    public static UserHashTag build(User user, HashTag hashTag, List<LinkHashTag> linkHashTagList) {
        return UserHashTag.builder()
                .usageCount((long) linkHashTagList.size())
                .user(user)
                .hashTag(hashTag)
                .build();
    }

    public void increaseUserHashTagCount(Long count) {
        this.usageCount = ++count;
    }

}