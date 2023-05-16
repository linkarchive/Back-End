package project.linkarchive.backend.bookmark.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.bookmark.response.QUserMarkedLinkListDetailResponse;
import project.linkarchive.backend.bookmark.response.UserMarkedLinkListDetailResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static project.linkarchive.backend.bookmark.domain.QBookMark.bookMark;

@Repository
public class BookMarkRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public BookMarkRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<UserMarkedLinkListDetailResponse> getMarkLinkList(Pageable pageable, Long lastMarkId) {
        return queryFactory
                .select(new QUserMarkedLinkListDetailResponse(
                        bookMark.id,
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
                        ltMarkId(lastMarkId)
                )
                .limit(pageable.getPageSize())
                .orderBy(bookMark.id.desc(), bookMark.link.bookMarkCount.desc())
                .fetch();
    }

    private BooleanExpression ltMarkId(Long lastMarkId) {
        return lastMarkId != null ? bookMark.link.id.lt(lastMarkId) : null;
    }

}