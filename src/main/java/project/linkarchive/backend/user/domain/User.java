package project.linkarchive.backend.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;
import project.linkarchive.backend.bookmark.domain.BookMark;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "email")
    private String email;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_introduce")
    private String introduce;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProfileImage profileImage;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private LinkarchiveToken linkarchiveToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserHashTag> userHashTagList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BookMark> bookMarkList = new ArrayList<>();

    @Builder
    public User(Long id, String socialId, String email, String name, String introduce) {
        this.id = id;
        this.socialId = socialId;
        this.email = email;
        this.name = name;
        this.introduce = introduce;
    }

}