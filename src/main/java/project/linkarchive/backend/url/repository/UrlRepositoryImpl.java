package project.linkarchive.backend.url.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.url.response.linkList.UserExcludedLinkListDetailResponse;
import project.linkarchive.backend.url.response.linkList.QLinkListDetailResponse;
import project.linkarchive.backend.url.response.userLinkList.QUserLinkListDetailResponse;
import project.linkarchive.backend.url.response.userLinkList.UserLinkListDetailResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static project.linkarchive.backend.url.domain.QUrl.url;

@Repository
public class UrlRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public UrlRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<UserLinkListDetailResponse> getUserLinkList(Pageable pageable, Long lastUrlId) {
        List<UserLinkListDetailResponse> content = queryFactory
                .select(new QUserLinkListDetailResponse(
                        url.id,
                        url.link,
                        url.title,
                        url.description,
                        url.thumbnail,
                        url.bookMarkCount
                ))
                .from(url)
                .where(
                        ltUrlId(lastUrlId)
                )
                .limit(pageable.getPageSize())
                .orderBy(url.id.desc())
                .fetch();

        return content;
    }

    public List<UserExcludedLinkListDetailResponse> getLinkList(Pageable pageable, Long lastUrlId) {
        List<UserExcludedLinkListDetailResponse> content = queryFactory
                .select(new QLinkListDetailResponse(
                        url.id,
                        url.link,
                        url.title,
                        url.description,
                        url.thumbnail,
                        url.bookMarkCount
                ))
                .from(url)
                .where(
                        ltUrlId(lastUrlId)
                )
                .limit(pageable.getPageSize())
                .orderBy(url.id.desc())
                .fetch();

        return content;
    }

    private BooleanExpression ltUrlId(Long lastUrlId) {
        return lastUrlId != null ? url.id.lt(lastUrlId) : null;
    }

}