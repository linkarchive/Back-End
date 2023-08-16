package project.linkarchive.backend.notification.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.advice.entityBase.CreatedEntity;
import project.linkarchive.backend.notification.enums.NotificationRead;
import project.linkarchive.backend.notification.enums.NotificationType;
import project.linkarchive.backend.user.domain.User;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Enumerated(EnumType.STRING)
    private NotificationRead notificationRead;

    private Long relationshipId;
    private Long bookmarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Notification(Long id, NotificationType notificationType, NotificationRead notificationRead, Long relationshipId, Long bookmarkId, User user) {
        this.id = id;
        this.notificationType = notificationType;
        this.notificationRead = notificationRead;
        this.relationshipId = relationshipId;
        this.bookmarkId = bookmarkId;
        this.user = user;
    }

}