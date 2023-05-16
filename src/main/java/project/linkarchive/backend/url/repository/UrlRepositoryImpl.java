package project.linkarchive.backend.url.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.url.response.linkList.QUserExcludedLinkListDetailResponse;
import project.linkarchive.backend.url.response.linkList.UserExcludedLinkListDetailResponse;
import project.linkarchive.backend.url.response.RefactorUserLinkList.QUrlResponse;
import project.linkarchive.backend.url.response.RefactorUserLinkList.UrlResponse;
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

    public List<UserLinkListDetailResponse> getUserLinkList(Pageable pageable, Long lastUrlId, Long userId) {
        return queryFactory
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
                        url.user.id.eq(userId),
                        ltUrlId(lastUrlId)
                )
                .limit(pageable.getPageSize())
                .orderBy(url.id.desc())
                .fetch();
    }

    public List<UserExcludedLinkListDetailResponse> getLinkList(Pageable pageable, Long lastUrlId, Long userId) {

        return queryFactory
                .select(new QUserExcludedLinkListDetailResponse(
                        url.user.id,
                        url.user.name,
                        url.user.profileImage.profileImage,
                        url.id,
                        url.link,
                        url.title,
                        url.description,
                        url.thumbnail,
                        url.bookMarkCount
                ))
                .from(url)
                .leftJoin(url.user)
                .leftJoin(url.user.profileImage)
                .where(
                        ltUrlId(lastUrlId),
                        url.user.id.ne(userId)
                )
                .limit(pageable.getPageSize())
                .orderBy(url.id.desc())
                .fetch();
    }

    public List<UrlResponse> getOtherLinkList(Long userId, Pageable pageable, Long lastUrlId) {
        return queryFactory
                .select(new QUrlResponse(
                        url.id,
                        url.link,
                        url.title,
                        url.description,
                        url.thumbnail,
                        url.bookMarkCount
                ))
                .from(url)
                .where(
                        url.user.id.eq(userId),
                        ltUrlId(lastUrlId)
                )
                .limit(pageable.getPageSize())
                .orderBy(url.id.desc())
                .fetch();
    }

    private BooleanExpression ltUrlId(Long lastUrlId) {
        return lastUrlId != null ? url.id.lt(lastUrlId) : null;
    }

}