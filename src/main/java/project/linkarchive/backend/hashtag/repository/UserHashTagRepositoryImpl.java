package project.linkarchive.backend.hashtag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.hashtag.domain.QUserHashtag;
import project.linkarchive.backend.hashtag.response.QTagResponse;
import project.linkarchive.backend.hashtag.response.TagResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static project.linkarchive.backend.advice.data.DataConstants.TAG_SIZE;
import static project.linkarchive.backend.hashtag.domain.QUserHashtag.userHashtag;

@Repository
public class UserHashTagRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public UserHashTagRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<TagResponse> getUserTagList(Long userId) {
        return queryFactory
                .select(new QTagResponse(
                        userHashtag.hashtag.id,
                        userHashtag.hashtag.tag
                ))
                .from(userHashtag)
                .where(userHashtag.user.id.eq(userId))
                .orderBy(userHashtag.usageCount.desc())
                .fetch();
    }

    public List<TagResponse> getUserTagList10(Long userId) {
        return queryFactory
                .select(new QTagResponse(
                        userHashtag.hashtag.id,
                        userHashtag.hashtag.tag
                ))
                .from(userHashtag)
                .where(userHashtag.user.id.eq(userId))
                .orderBy(userHashtag.usageCount.desc())
                .limit(TAG_SIZE)
                .fetch();
    }

}