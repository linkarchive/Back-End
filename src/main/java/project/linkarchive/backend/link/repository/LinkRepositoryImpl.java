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
import static project.linkarchive.backend.link.domain.QLinkHashtag.linkHashtag;
import static project.linkarchive.backend.link.enums.LinkStatus.ACTIVE;
import static project.linkarchive.backend.link.enums.LinkStatus.TRASH;
import static project.linkarchive.backend.profileImage.domain.QProfileImage.profileImage;

@Repository
public class LinkRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public LinkRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<LinkResponse> getMyLinkList(Long tagId, Long linkId, Pageable pageable, Long loginUserId) {
        return queryFactory
                .select(new QLinkResponse(
                        link.id,
                        link.url,
                        link.title,
                        link.description,
                        link.thumbnail,
                        link.bookmarkCount,
                        link.createdAt,
                        link.updatedAt
                ))
                .from(link)
                .distinct()
                .leftJoin(link.linkHashtagList, linkHashtag)
                .where(
                        link.user.id.eq(loginUserId),
                        link.linkStatus.eq(ACTIVE),
                        ltUrlId(linkId),
                        containTag(tagId)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(link.id.desc())
                .fetch();
    }

    public List<LinkResponse> getUserLinkList(Long userId, Long tagId, Long linkId, Pageable pageable) {
        return queryFactory
                .select(new QLinkResponse(
                        link.id,
                        link.url,
                        link.title,
                        link.description,
                        link.thumbnail,
                        link.bookmarkCount,
                        link.createdAt,
                        link.updatedAt
                ))
                .from(link)
                .distinct()
                .leftJoin(link.linkHashtagList, linkHashtag)
                .where(
                        link.user.id.eq(userId),
                        link.linkStatus.eq(ACTIVE),
                        ltUrlId(linkId),
                        containTag(tagId)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(link.id.desc())
                .fetch();
    }

    public List<ArchiveResponse> getLinkArchive(Long tagId, Long linkId, Pageable pageable) {
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
                        link.bookmarkCount,
                        link.createdAt,
                        link.updatedAt
                ))
                .from(link)
                .distinct()
                .leftJoin(link.user.profileImage, profileImage)
                .leftJoin(link.linkHashtagList, linkHashtag)
                .where(
                        link.linkStatus.eq(ACTIVE),
                        ltUrlId(linkId),
                        containTag(tagId)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(link.id.desc())
                .fetch();
    }

    public List<TrashLinkResponse> getTrashLinkList(Long tagId, Long linkId, Pageable pageable, Long loginUserId) {
        return queryFactory
                .select(new QTrashLinkResponse(
                        link.id,
                        link.url,
                        link.title,
                        link.description,
                        link.thumbnail,
                        link.bookmarkCount,
                        link.createdAt
                ))
                .from(link)
                .distinct()
                .leftJoin(link.linkHashtagList, linkHashtag)
                .where(
                        link.user.id.eq(loginUserId),
                        link.linkStatus.eq(TRASH),
                        ltUrlId(linkId),
                        containTag(tagId)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(link.id.desc())
                .fetch();
    }

    private BooleanExpression ltUrlId(Long linkId) {
        return linkId != null ? link.id.lt(linkId) : null;
    }

    private BooleanExpression containTag(Long tagId) {
        return tagId != null ? linkHashtag.hashtag.id.eq(tagId) : null;
    }

}