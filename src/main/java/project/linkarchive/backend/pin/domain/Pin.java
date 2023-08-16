package project.linkarchive.backend.pin.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;
import project.linkarchive.backend.user.domain.User;

import javax.persistence.*;

import static project.linkarchive.backend.advice.data.DataConstants.DEFAULT_COUNT;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pin extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int linkCount;
    private int bookmarkCount;

    @OneToOne(mappedBy = "profileImage", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @Builder
    public Pin(Long id, int linkCount, int bookmarkCount) {
        this.id = id;
        this.linkCount = linkCount;
        this.bookmarkCount = bookmarkCount;
    }

    public static Pin create() {
        return Pin.builder()
                .linkCount(DEFAULT_COUNT)
                .bookmarkCount(DEFAULT_COUNT)
                .build();
    }

}
