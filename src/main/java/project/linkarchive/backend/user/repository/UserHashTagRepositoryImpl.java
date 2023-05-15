package project.linkarchive.backend.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.hashtag.response.QTagListDetailResponse;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static project.linkarchive.backend.user.domain.QUserHashTag.*;

@Repository
public class UserHashTagRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public UserHashTagRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<TagListDetailResponse> getTagListLimit30(Long userId) {
        return queryFactory
                .select(new QTagListDetailResponse(
                        userHashTag.hashTag.tag
                ))
                .from(userHashTag)
                .where(userHashTag.user.id.eq(userId))
                .orderBy(userHashTag.usageCount.desc())
                .limit(30)
                .fetch();
    }

    public List<TagListDetailResponse> getUserTagList(Long userId) {
        return queryFactory
                .select(new QTagListDetailResponse(
                        userHashTag.hashTag.tag
                ))
                .from(userHashTag)
                .where(userHashTag.user.id.eq(userId))
                .orderBy(userHashTag.usageCount.desc())
                .fetch();
    }

}