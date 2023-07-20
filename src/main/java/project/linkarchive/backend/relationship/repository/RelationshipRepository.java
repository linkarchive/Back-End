package project.linkarchive.backend.relationship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.relationship.domain.Relationship;

import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    Optional<Relationship> findByFolloweeIdAndFollowerId(Long followeeId, Long followerId);

}