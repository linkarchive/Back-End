package project.linkarchive.backend.url.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.url.response.userLinkList.QUserUrlLinkListDetailResponse;
import project.linkarchive.backend.url.response.userLinkList.UserUrlLinkListDetailResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static project.linkarchive.backend.url.domain.QUrl.url;
import static project.linkarchive.backend.url.domain.QUrlHashTag.urlHashTag;

@Repository
public class UrlRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public UrlRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<UserUrlLinkListDetailResponse> getUserLinkList(Pageable pageable, Long lastUrlId) {
        List<UserUrlLinkListDetailResponse> content = queryFactory
                .select(new QUserUrlLinkListDetailResponse(
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