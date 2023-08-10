package project.linkarchive.backend.bookmark.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.bookmark.response.MarkResponse;
import project.linkarchive.backend.bookmark.response.QMarkResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static project.linkarchive.backend.bookmark.domain.QBookmark.bookmark;
import static project.linkarchive.backend.link.domain.QLinkHashtag.linkHashtag;
import static project.linkarchive.backend.link.enums.LinkStatus.ACTIVE;

@Repository
public class BookMarkRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public BookMarkRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<MarkResponse> getMyMarkLinkList(Long tagId, Long markId, Pageable pageable, Long userId) {
        return queryFactory
                .select(new QMarkResponse(
                        bookmark.id,
                        bookmark.link.id,
                        bookmark.link.url,
                        bookmark.link.title,
                        bookmark.link.description,
                        bookmark.link.thumbnail,
                        bookmark.link.bookmarkCount,
                        bookmark.createdAt
                ))
                .from(bookmark)
                .distinct()
                .leftJoin(bookmark.link.linkHashtagList, linkHashtag)
                .where(
                        bookmark.user.id.eq(userId),
                        bookmark.link.linkStatus.eq(ACTIVE),
                        ltLinkId(markId),
                        containTag(tagId)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(bookmark.id.desc())
                .fetch();
    }

    public List<MarkResponse> getUserMarkLinkList(Long userId, Long tagId, Long markId, Pageable pageable) {
        return queryFactory
                .select(new QMarkResponse(
                        bookmark.id,
                        bookmark.link.id,
                        bookmark.link.url,
                        bookmark.link.title,
                        bookmark.link.description,
                        bookmark.link.thumbnail,
                        bookmark.link.bookmarkCount,
                        bookmark.createdAt
                ))
                .from(bookmark)
                .distinct()
                .leftJoin(bookmark.link.linkHashtagList, linkHashtag)
                .where(
                        bookmark.user.id.eq(userId),
                        bookmark.link.linkStatus.eq(ACTIVE),
                        ltLinkId(markId),
                        containTag(tagId)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(bookmark.id.desc())
                .fetch();
    }

    private BooleanExpression ltLinkId(Long markId) {
        return markId != null ? bookmark.id.lt(markId) : null;
    }

    private BooleanExpression containTag(Long tagId) {
        return tagId != null ? linkHashtag.hashtag.id.eq(tagId) : null;
    }

}