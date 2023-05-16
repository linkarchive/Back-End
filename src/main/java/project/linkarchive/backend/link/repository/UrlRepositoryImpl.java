package project.linkarchive.backend.link.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.link.response.RefactorUserLinkList.LinkResponse;
import project.linkarchive.backend.link.response.linkList.QUserExcludedLinkListDetailResponse;
import project.linkarchive.backend.link.response.linkList.UserExcludedLinkListDetailResponse;
import project.linkarchive.backend.link.response.userLinkList.QUserLinkListDetailResponse;
import project.linkarchive.backend.link.response.userLinkList.UserLinkListDetailResponse;
import project.linkarchive.backend.url.response.RefactorUserLinkList.QLinkResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static project.linkarchive.backend.link.domain.QLink.link;

@Repository
public class UrlRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public UrlRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<UserLinkListDetailResponse> getUserLinkList(Pageable pageable, Long lastUrlId, Long userId) {
        return queryFactory
                .select(new QUserLinkListDetailResponse(
                        link.id,
                        link.url,
                        link.title,
                        link.description,
                        link.thumbnail,
                        link.bookMarkCount
                ))
                .from(link)
                .where(
                        link.user.id.eq(userId),
                        ltUrlId(lastUrlId)
                )
                .limit(pageable.getPageSize())
                .orderBy(link.id.desc())
                .fetch();
    }

    public List<UserExcludedLinkListDetailResponse> getLinkList(Pageable pageable, Long lastUrlId, Long userId) {

        return queryFactory
                .select(new QUserExcludedLinkListDetailResponse(
                        link.user.id,
                        link.user.name,
                        link.user.profileImage.profileImage,
                        link.id,
                        link.url,
                        link.title,
                        link.description,
                        link.thumbnail,
                        link.bookMarkCount
                ))
                .from(link)
                .leftJoin(link.user)
                .leftJoin(link.user.profileImage)
                .where(
                        ltUrlId(lastUrlId),
                        link.user.id.ne(userId)
                )
                .limit(pageable.getPageSize())
                .orderBy(link.id.desc())
                .fetch();
    }

    public List<LinkResponse> getOtherLinkList(Long userId, Pageable pageable, Long lastUrlId) {
        return queryFactory
                .select(new QLinkResponse(
                        link.id,
                        link.url,
                        link.title,
                        link.description,
                        link.thumbnail,
                        link.bookMarkCount
                ))
                .from(link)
                .where(
                        link.user.id.eq(userId),
                        ltUrlId(lastUrlId)
                )
                .limit(pageable.getPageSize())
                .orderBy(link.id.desc())
                .fetch();
    }

    private BooleanExpression ltUrlId(Long lastUrlId) {
        return lastUrlId != null ? link.id.lt(lastUrlId) : null;
    }

}