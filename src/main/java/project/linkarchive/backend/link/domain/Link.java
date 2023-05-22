package project.linkarchive.backend.link.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.link.request.CreateLinkRequest;
import project.linkarchive.backend.user.domain.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Link extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long id;

    private String url;
    private String title;

    @Column(length = 500)
    private String description;

    private String thumbnail;
    private Long bookMarkCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "link", cascade = CascadeType.ALL)
    private List<LinkHashTag> linkHashTagList = new ArrayList<>();

    @OneToMany(mappedBy = "link", cascade = CascadeType.ALL)
    private List<BookMark> bookMarkList = new ArrayList<>();

    @Builder
    public Link(Long id, String url, String title, String description, String thumbnail, Long bookMarkCount, User user) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.user = user;
    }

    public static Link build(CreateLinkRequest request, User user) {
        return Link.builder()
                .url(request.getUrl())
                .title(request.getTitle())
                .description(request.getDescription())
                .thumbnail(request.getThumbnail())
                .bookMarkCount(0L)
                .user(user)
                .build();
    }

}