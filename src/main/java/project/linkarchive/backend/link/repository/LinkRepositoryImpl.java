package project.linkarchive.backend.link.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.link.response.linkList.LinkResponse;
import project.linkarchive.backend.link.response.linkList.QLinkResponse;
import project.linkarchive.backend.link.response.linkarchive.ArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.QArchiveResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static project.linkarchive.backend.link.domain.QLink.link;

@Repository
public class LinkRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public LinkRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<LinkResponse> getUserLinkList(Long userId, Pageable pageable, Long lastLinkLid) {
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
                        ltUrlId(lastLinkLid)
                )
                .limit(pageable.getPageSize())
                .orderBy(link.id.desc())
                .fetch();
    }

    public List<ArchiveResponse> getLinkArchive(Pageable pageable, Long lastLinkId, Long userId) {
        return queryFactory
                .select(new QArchiveResponse(
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
                        ltUrlId(lastLinkId),
                        link.user.id.ne(userId)
                )
                .limit(pageable.getPageSize())
                .orderBy(link.id.desc())
                .fetch();
    }

    private BooleanExpression ltUrlId(Long lastLinkId) {
        return lastLinkId != null ? link.id.lt(lastLinkId) : null;
    }

}