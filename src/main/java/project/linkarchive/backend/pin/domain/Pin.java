package project.linkarchive.backend.pin.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pin extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int linkCount;
    private int bookmarkCount;

    @Builder
    public Pin(Long id, int linkCount, int bookmarkCount) {
        this.id = id;
        this.linkCount = linkCount;
        this.bookmarkCount = bookmarkCount;
    }

}
