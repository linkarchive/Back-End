package project.linkarchive.backend.url.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.url.request.UrlCreateRequest;
import project.linkarchive.backend.user.domain.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    private Long id;

    private String link;
    private String title;

    @Column(length = 500)
    private String description;

    private String thumbnail;
    private Long bookMarkCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL)
    private List<UrlHashTag> urlHashTagList = new ArrayList<>();

    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL)
    private List<BookMark> bookMarkList = new ArrayList<>();

    @Builder
    public Url(Long id, String link, String title, String description, String thumbnail, Long bookMarkCount, User user) {
        this.id = id;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.user = user;
    }

    public static Url of(UrlCreateRequest request) {
        return Url.builder()
                .link(request.getLink())
                .title(request.getTitle())
                .description(request.getDescription())
                .thumbnail(request.getThumbnail())
                .bookMarkCount(7L)
                .build();
    }

    public List<String> getUrlHashTagList() {
        return urlHashTagList.stream()
                .map((e)->e.getHashTag().getTag())
                .toList();
    }

}