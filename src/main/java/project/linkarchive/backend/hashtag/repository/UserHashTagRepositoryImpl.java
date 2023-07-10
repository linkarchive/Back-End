package project.linkarchive.backend.hashtag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.hashtag.response.QTagResponse;
import project.linkarchive.backend.hashtag.response.TagResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static project.linkarchive.backend.hashtag.domain.QUserHashTag.userHashTag;

@Repository
public class UserHashTagRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public UserHashTagRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<TagResponse> getUserTagList(String nickname) {
        return queryFactory
                .select(new QTagResponse(
                        userHashTag.hashTag.id,
                        userHashTag.hashTag.tag
                ))
                .from(userHashTag)
                .where(userHashTag.user.nickname.eq(nickname))
                .orderBy(userHashTag.usageCount.desc())
                .fetch();
    }

    public List<TagResponse> getLimitedTagList(String nickname, int size) {
        return queryFactory
                .select(new QTagResponse(
                        userHashTag.hashTag.id,
                        userHashTag.hashTag.tag
                ))
                .from(userHashTag)
                .where(userHashTag.user.nickname.eq(nickname))
                .orderBy(userHashTag.usageCount.desc())
                .limit(size)
                .fetch();
    }

}