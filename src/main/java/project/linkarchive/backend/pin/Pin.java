package project.linkarchive.backend.pin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.TimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Pin extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int linkCount;
    private int bookmarkCount;

    public Pin(Long id, int linkCount, int bookmarkCount) {
        this.id = id;
        this.linkCount = linkCount;
        this.bookmarkCount = bookmarkCount;
    }

}
