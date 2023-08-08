package project.linkarchive.backend.hashtag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import project.linkarchive.backend.hashtag.response.QTagResponse;
import project.linkarchive.backend.hashtag.response.TagResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static project.linkarchive.backend.hashtag.domain.QHashTag.hashTag;

@Repository
public class HashTagRepositoryImpl {
    private final JPAQueryFactory queryFactory;

    public HashTagRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<TagResponse> getArchiveTagList() {
        return queryFactory
                .select(new QTagResponse(
                        hashTag.id,
                        hashTag.tag
                ))
                .from(hashTag)
                .where(hashTag.id.between(9L, 18L))
                .fetch();
    }
}
