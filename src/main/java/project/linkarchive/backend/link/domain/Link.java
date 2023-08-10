package project.linkarchive.backend.link.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;
import project.linkarchive.backend.bookmark.domain.Bookmark;
import project.linkarchive.backend.isLinkRead.domain.IsLinkRead;
import project.linkarchive.backend.link.enums.LinkStatus;
import project.linkarchive.backend.link.request.CreateLinkRequest;
import project.linkarchive.backend.user.domain.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static project.linkarchive.backend.advice.data.DataConstants.BOOKMARK_DEFAULT_COUNT;
import static project.linkarchive.backend.link.enums.LinkStatus.ACTIVE;
import static project.linkarchive.backend.link.enums.LinkStatus.TRASH;

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
    private int bookmarkCount;

    @Enumerated(EnumType.STRING)
    private LinkStatus linkStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "link", cascade = CascadeType.ALL)
    private List<LinkHashtag> linkHashtagList = new ArrayList<>();

    @OneToMany(mappedBy = "link", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<IsLinkRead> isLinkReadList = new ArrayList<>();

    @Builder
    public Link(Long id, String url, String title, String description, String thumbnail, int bookmarkCount, LinkStatus linkStatus, User user) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookmarkCount = bookmarkCount;
        this.linkStatus = linkStatus.ACTIVE;
        this.user = user;
    }

    public static Link create(CreateLinkRequest request, User user) {
        return Link.builder()
                .url(request.getUrl())
                .title(request.getTitle())
                .description(request.getDescription())
                .thumbnail(request.getThumbnail())
                .bookmarkCount(BOOKMARK_DEFAULT_COUNT)
                .linkStatus(ACTIVE)
                .user(user)
                .build();
    }

    public void delete() {
        this.linkStatus = TRASH;
    }

}