package project.linkarchive.backend.link.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.link.response.linkList.LinkResponse;
import project.linkarchive.backend.link.response.linkList.QLinkResponse;
import project.linkarchive.backend.link.response.linkarchive.ArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.QArchiveResponse;
import project.linkarchive.backend.link.response.trash.QTrashLinkResponse;
import project.linkarchive.backend.link.response.trash.TrashLinkResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static project.linkarchive.backend.link.domain.QLink.link;
import static project.linkarchive.backend.link.domain.QLinkHashTag.linkHashTag;
import static project.linkarchive.backend.link.enums.LinkStatus.ACTIVE;
import static project.linkarchive.backend.link.enums.LinkStatus.TRASH;
import static project.linkarchive.backend.profileImage.domain.QProfileImage.profileImage;

@Repository
public class LinkRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public LinkRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<LinkResponse> getMyLinkList(Long userId, Pageable pageable, Long lastLinkLid, String tag) {
        return queryFactory
                .select(new QLinkResponse(
                        link.id,
                        link.url,
                        link.title,
                        link.description,
                        link.thumbnail,
                        link.bookMarkCount,
                        link.createdAt,
                        link.updatedAt
                ))
                .from(link)
                .distinct()
                .leftJoin(link.linkHashTagList, linkHashTag)
                .where(
                        link.user.id.eq(userId),
                        link.linkStatus.eq(ACTIVE),
                        ltUrlId(lastLinkLid),
                        containTag(tag)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(link.id.desc())
                .fetch();
    }

    public List<LinkResponse> getUserLinkList(String nickname, Pageable pageable, Long lastLinkLid, String tag) {
        return queryFactory
                .select(new QLinkResponse(
                        link.id,
                        link.url,
                        link.title,
                        link.description,
                        link.thumbnail,
                        link.bookMarkCount,
                        link.createdAt,
                        link.updatedAt
                ))
                .from(link)
                .distinct()
                .leftJoin(link.linkHashTagList, linkHashTag)
                .where(
                        link.user.nickname.eq(nickname),
                        link.linkStatus.eq(ACTIVE),
                        ltUrlId(lastLinkLid),
                        containTag(tag)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(link.id.desc())
                .fetch();
    }

    public List<ArchiveResponse> getLinkArchive(Pageable pageable, Long lastLinkId, String tag) {
        return queryFactory
                .select(new QArchiveResponse(
                        link.user.id,
                        link.user.nickname,
                        link.user.profileImage.profileImageFilename,
                        link.id,
                        link.url,
                        link.title,
                        link.description,
                        link.thumbnail,
                        link.bookMarkCount,
                        link.createdAt,
                        link.updatedAt
                ))
                .from(link)
                .distinct()
                .leftJoin(link.user.profileImage, profileImage)
                .leftJoin(link.linkHashTagList, linkHashTag)
                .where(
                        link.linkStatus.eq(ACTIVE),
                        ltUrlId(lastLinkId),
                        containTag(tag)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(link.id.desc())
                .fetch();
    }

    public List<TrashLinkResponse> getTrashLinkList(String tag, Long lastLinkId, Pageable pageable, Long loginUserId) {
        return queryFactory
                .select(new QTrashLinkResponse(
                        link.id,
                        link.url,
                        link.title,
                        link.description,
                        link.thumbnail,
                        link.bookMarkCount,
                        link.createdAt
                ))
                .from(link)
                .distinct()
                .leftJoin(link.linkHashTagList, linkHashTag)
                .where(
                        link.user.id.eq(loginUserId),
                        link.linkStatus.eq(TRASH),
                        ltUrlId(lastLinkId),
                        containTag(tag)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(link.id.desc())
                .fetch();
    }

    private BooleanExpression ltUrlId(Long lastLinkId) {
        return lastLinkId != null ? link.id.lt(lastLinkId) : null;
    }

    private BooleanExpression containTag(String tag) {
        return tag != null ? linkHashTag.hashTag.tag.eq(tag) : null;
    }

}