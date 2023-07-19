package project.linkarchive.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.user.domain.Relationship;

import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    Optional<Relationship> findByFolloweeAndFollower(Long followee, Long follower);

}