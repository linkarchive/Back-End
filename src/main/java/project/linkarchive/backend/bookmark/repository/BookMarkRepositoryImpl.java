package project.linkarchive.backend.bookmark.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.bookmark.response.QUserMarkedLinkListDetailResponse;
import project.linkarchive.backend.bookmark.response.UserMarkedLinkListDetailResponse;
import project.linkarchive.backend.url.response.otherUserLinkList.QUrlResponse;
import project.linkarchive.backend.url.response.otherUserLinkList.UrlResponse;

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
                        ltMarkId(lastMarkId)
                )
                .limit(pageable.getPageSize())
                .orderBy(bookMark.id.desc(), bookMark.url.bookMarkCount.desc())
                .fetch();
    }

    public List<UrlResponse> getMarkLinkList(Long userId, Pageable pageable, Long lastMarkId) {
        return queryFactory
                .select(new QUrlResponse(
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