package project.linkarchive.backend.hashtag.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.CreatedEntity;
import project.linkarchive.backend.link.domain.LinkHashtag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    private String tag;

    @OneToMany(mappedBy = "hashtag", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<UserHashtag> userHashtagList = new ArrayList<>();

    @OneToMany(mappedBy = "hashtag", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<LinkHashtag> linkHashtagList = new ArrayList<>();

    @Builder
    public Hashtag(Long id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public static Hashtag create(String tag) {
        return Hashtag.builder()
                .tag(tag)
                .build();
    }

}