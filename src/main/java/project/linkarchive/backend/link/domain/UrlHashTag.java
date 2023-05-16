package project.linkarchive.backend.link.domain;

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
public class UrlHashTag extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_hashtag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "link_id")
    private Link link;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hashtag_id")
    private HashTag hashTag;

    @Builder
    public UrlHashTag(Long id, Link link, HashTag hashTag) {
        this.id = id;
        this.link = link;
        this.hashTag = hashTag;
    }

    public static UrlHashTag of(Link link, HashTag hashTag) {
        return UrlHashTag.builder()
                .link(link)
                .hashTag(hashTag)
                .build();
    }

}