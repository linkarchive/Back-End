package project.linkarchive.backend.relationship.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.AlreadyExistException;
import project.linkarchive.backend.advice.exception.custom.InvalidException;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.relationship.domain.Relationship;
import project.linkarchive.backend.relationship.repository.RelationshipRepository;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Service
@Transactional
public class RelationshipApiService {

    private final UserRepository userRepository;
    private final RelationshipRepository relationshipRepository;

    public RelationshipApiService(UserRepository userRepository, RelationshipRepository relationshipRepository) {
        this.userRepository = userRepository;
        this.relationshipRepository = relationshipRepository;
    }

    public void followUser(Long followeeId, Long followerId) {
        validateFollowRequest(followeeId, followerId);
        checkFollowStatus(followeeId, followerId);

        User followee = findRelatedUser(followeeId);
        User follower = findRelatedUser(followerId);

        Relationship relationship = Relationship.create(followee, follower);

        userRepository.increaseFollowerCount(followeeId);
        userRepository.increaseFollowingCount(followerId);
        relationshipRepository.save(relationship);
    }

    public void unfollowUser(Long followeeId, Long followerId) {
        validateFollowRequest(followeeId, followerId);
        Relationship relationship = checkUnFollowStatus(followeeId, followerId);

        userRepository.decreaseFollowerCount(followeeId);
        userRepository.decreaseFollowingCount(followerId);
        relationshipRepository.delete(relationship);
    }

    private void validateFollowRequest(Long followeeId, Long followerId) {
        if (followerId.equals(followeeId)) {
            throw new InvalidException(FOLLOW_FAILED);
        }
    }

    private void checkFollowStatus(Long followeeId, Long followerId) {
        relationshipRepository.findByFolloweeIdAndFollowerId(followeeId, followerId)
                .ifPresent(i -> {
                    throw new AlreadyExistException(ALREADY_FOLLOWED);
                });
    }

    private Relationship checkUnFollowStatus(Long followeeId, Long followerId) {
        return relationshipRepository.findByFolloweeIdAndFollowerId(followeeId, followerId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_FOLLOW_STATUS));
    }

    private User findRelatedUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

}
