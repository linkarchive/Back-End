package project.linkarchive.backend.advice.entityBase;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class CreatedEntity {

    @Column(updatable = false)
    @CreatedDate
    private LocalDate createdAt;

}