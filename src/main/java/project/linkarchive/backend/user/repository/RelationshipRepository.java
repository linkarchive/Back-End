package project.linkarchive.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.linkarchive.backend.user.domain.Relationship;

import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

//    //TODO: 팔로워 수, 팔로잉 수
//    @Query("SELECT count(r.id) from Relationship r " +
//            "WHERE r.followerId=:userId ")
//    int followerCount(Long userId);
//
//    @Query("SELECT count(r.id) from Relationship r " +
//            "WHERE r.followingId=:userId ")
//    int followingCount(Long userId);

    Optional<Relationship> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

}