package project.linkarchive.backend.link.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.CreatedEntity;
import project.linkarchive.backend.hashtag.domain.Hashtag;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LinkHashtag extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_hashtag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "link_id")
    private Link link;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @Builder
    public LinkHashtag(Long id, Link link, Hashtag hashtag) {
        this.id = id;
        this.link = link;
        this.hashtag = hashtag;
    }

    public static LinkHashtag create(Link link, Hashtag hashTag) {
        return LinkHashtag.builder()
                .link(link)
                .hashtag(hashTag)
                .build();
    }

}