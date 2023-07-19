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

    public void followUser(String nickname, Long followerId) {
        Long followeeId = validateFollowRequest(nickname, followerId);

        checkFollowStatus(followeeId, followerId);

        Relationship relationship = Relationship.create(followerId, followeeId);
        relationshipRepository.save(relationship);

        userRepository.increaseFollowerCount(followeeId);
        userRepository.increaseFollowingCount(followerId);
    }

    public void unfollowUser(String followeeNickname, Long followerId) {
        Long followeeId = validateFollowRequest(followeeNickname, followerId);

        Relationship relationship = checkUnFollowStatus(followeeId, followerId);

        relationshipRepository.delete(relationship);

        userRepository.decreaseFollowerCount(followeeId);
        userRepository.decreaseFollowingCount(followerId);
    }

    private void checkFollowStatus(Long followeeId, Long followerId) {
        relationshipRepository.findByFolloweeAndFollower(followeeId, followerId)
                .ifPresent(i -> {
                    throw new AlreadyExistException(ALREADY_FOLLOWED);
                });
    }

    private Relationship checkUnFollowStatus(Long followeeId, Long followerId) {
        Relationship relationship = relationshipRepository.findByFolloweeAndFollower(followeeId, followerId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_FOLLOW_STATUS));

        return relationship;
    }

    private Long validateFollowRequest(String followeeNickname, Long followerId) {
        Long followeeId = findFolloweeId(followeeNickname);

        if (followerId.equals(followeeId)) {
            throw new InvalidException(FOLLOW_FAILED);
        }

        return followeeId;
    }

    private Long findFolloweeId(String followeeNickname) {
        User followee = userRepository.findByNickname(followeeNickname)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));

        return followee.getId();
    }

}
