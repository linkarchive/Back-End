package project.linkarchive.backend.relationship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.linkarchive.backend.relationship.domain.Relationship;
import project.linkarchive.backend.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    Optional<Relationship> findByFolloweeIdAndFollowerId(Long followeeId, Long followerId);

    @Query("SELECT r.follower FROM Relationship r " +
            "WHERE r.followee.id=:followeeId")
    List<User> findFollowerIdByFolloweeId(Long followeeId);
}