package project.linkarchive.backend.bookmark.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.link.response.linkList.LinkResponse;
import project.linkarchive.backend.link.response.linkList.QLinkResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static project.linkarchive.backend.bookmark.domain.QBookMark.bookMark;
import static project.linkarchive.backend.link.domain.QLink.link;
import static project.linkarchive.backend.link.domain.QLinkHashTag.linkHashTag;


@Repository
public class BookMarkRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public BookMarkRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<LinkResponse> getMyMarkLinkList(Long userId, Long lastLinkId, Pageable pageable, String tag) {
        return queryFactory
                .select(new QLinkResponse(
                        bookMark.link.id,
                        bookMark.link.url,
                        bookMark.link.title,
                        bookMark.link.description,
                        bookMark.link.thumbnail,
                        bookMark.link.bookMarkCount
                ))
                .from(bookMark)
                .leftJoin(bookMark.link.linkHashTagList, linkHashTag)
                .where(
                        bookMark.user.id.eq(userId),
                        ltLinkId(lastLinkId),
                        containTag(tag)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(bookMark.id.desc())
                .fetch();
    }

    public List<LinkResponse> getUserMarkLinkList(String nickname, Long lastLinkId, Pageable pageable, String tag) {
        return queryFactory
                .select(new QLinkResponse(
                        bookMark.link.id,
                        bookMark.link.url,
                        bookMark.link.title,
                        bookMark.link.description,
                        bookMark.link.thumbnail,
                        bookMark.link.bookMarkCount
                ))
                .from(bookMark)
                .leftJoin(bookMark.link.linkHashTagList, linkHashTag)
                .where(
                        bookMark.user.nickname.eq(nickname),
                        ltLinkId(lastLinkId),
                        containTag(tag)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(bookMark.id.desc())
                .fetch();
    }

    private BooleanExpression ltLinkId(Long lastMarkId) {
        return lastMarkId != null ? bookMark.link.id.lt(lastMarkId) : null;
    }

    private BooleanExpression containTag(String tag) {
        return tag != null ? linkHashTag.hashTag.tag.eq(tag) : null;
    }

}