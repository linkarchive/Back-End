package project.linkarchive.backend.hashtag.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.CreatedEntity;
import project.linkarchive.backend.hashtag.request.CreateTagRequest;
import project.linkarchive.backend.link.domain.LinkHashTag;
import project.linkarchive.backend.user.domain.UserHashTag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashTag extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    private String tag;

    @OneToMany(mappedBy = "hashTag", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserHashTag> userHashTags = new ArrayList<>();

    @OneToMany(mappedBy = "hashTag", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LinkHashTag> linkHashTagList = new ArrayList<>();

    @Builder
    public HashTag(Long id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public static HashTag build(CreateTagRequest request) {
        return HashTag.builder()
                .tag(request.getTag())
                .build();
    }

    public static HashTag build(String tag) {
        return HashTag.builder()
                .tag(tag)
                .build();
    }

}