package project.linkarchive.backend.bookmark.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.bookmark.response.MarkResponse;
import project.linkarchive.backend.bookmark.response.QMarkResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static project.linkarchive.backend.bookmark.domain.QBookMark.bookMark;
import static project.linkarchive.backend.link.domain.QLinkHashTag.linkHashTag;
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
                        bookMark.id,
                        bookMark.link.id,
                        bookMark.link.url,
                        bookMark.link.title,
                        bookMark.link.description,
                        bookMark.link.thumbnail,
                        bookMark.link.bookMarkCount,
                        bookMark.createdAt
                ))
                .from(bookMark)
                .distinct()
                .leftJoin(bookMark.link.linkHashTagList, linkHashTag)
                .where(
                        bookMark.user.id.eq(userId),
                        bookMark.link.linkStatus.eq(ACTIVE),
                        ltLinkId(markId),
                        containTag(tagId)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(bookMark.id.desc())
                .fetch();
    }

    public List<MarkResponse> getUserMarkLinkList(Long userId, Long tagId, Long markId, Pageable pageable) {
        return queryFactory
                .select(new QMarkResponse(
                        bookMark.id,
                        bookMark.link.id,
                        bookMark.link.url,
                        bookMark.link.title,
                        bookMark.link.description,
                        bookMark.link.thumbnail,
                        bookMark.link.bookMarkCount,
                        bookMark.createdAt
                ))
                .from(bookMark)
                .distinct()
                .leftJoin(bookMark.link.linkHashTagList, linkHashTag)
                .where(
                        bookMark.user.id.eq(userId),
                        bookMark.link.linkStatus.eq(ACTIVE),
                        ltLinkId(markId),
                        containTag(tagId)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(bookMark.id.desc())
                .fetch();
    }

    private BooleanExpression ltLinkId(Long markId) {
        return markId != null ? bookMark.id.lt(markId) : null;
    }

    private BooleanExpression containTag(Long tagId) {
        return tagId != null ? linkHashTag.hashTag.id.eq(tagId) : null;
    }

}