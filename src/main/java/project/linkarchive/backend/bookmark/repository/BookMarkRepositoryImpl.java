package project.linkarchive.backend.bookmark.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.url.response.RefactorUserLinkList.LinkResponse;
import project.linkarchive.backend.url.response.RefactorUserLinkList.QLinkResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static project.linkarchive.backend.bookmark.domain.QBookMark.bookMark;

@Repository
public class BookMarkRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public BookMarkRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<LinkResponse> getMarkLinkList(Long userId, Pageable pageable, Long lastMarkId) {
        return queryFactory
                .select(new QLinkResponse(
                        bookMark.url.id,
                        bookMark.url.link,
                        bookMark.url.title,
                        bookMark.url.description,
                        bookMark.url.thumbnail,
                        bookMark.url.bookMarkCount
                ))
                .from(bookMark)
                .leftJoin(bookMark.url)
                .where(
                        bookMark.user.id.eq(userId),
                        ltMarkId(lastMarkId)
                )
                .limit(pageable.getPageSize())
                .orderBy(bookMark.id.desc())
                .fetch();
    }

    private BooleanExpression ltMarkId(Long lastMarkId) {
        return lastMarkId != null ? bookMark.url.id.lt(lastMarkId) : null;
    }

}