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


@Repository
public class BookMarkRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public BookMarkRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<LinkResponse> getMarkLinkList(Long userId, Long lastMarkId, Pageable pageable) {
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
                .leftJoin(bookMark.link)
                .where(
                        bookMark.user.id.eq(userId),
                        ltMarkId(lastMarkId)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(bookMark.id.desc())
                .fetch();
    }

    private BooleanExpression ltMarkId(Long lastMarkId) {
        return lastMarkId != null ? bookMark.link.id.lt(lastMarkId) : null;
    }

}