package project.linkarchive.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.user.domain.Relationship;

import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    @Query("SELECT sum(r.followingId) from Relationship r " +
            "WHERE r.followerId=:userId ")
    Long followerCount(Long userId);

    @Query("SELECT sum(r.followerId) from Relationship r " +
            "WHERE r.followingId=:userId ")
    Long followingCount(Long userId);

    Optional<Relationship> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

}