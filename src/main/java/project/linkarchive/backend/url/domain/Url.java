package project.linkarchive.backend.url.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;
import project.linkarchive.backend.bookmark.domain.BookMark;
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

    private String title;
    private String url;
    private Long bookMarkCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL)
    private List<UrlHashTag> urlHashTagList = new ArrayList<>();

    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL)
    private List<BookMark> bookMarkList = new ArrayList<>();

    @Builder
    public Url(Long id, String title, String url, Long bookMarkCount, User user, List<UrlHashTag> urlHashTagList, List<BookMark> bookMarkList) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.bookMarkCount = bookMarkCount;
        this.user = user;
        this.urlHashTagList = urlHashTagList;
        this.bookMarkList = bookMarkList;
    }

}