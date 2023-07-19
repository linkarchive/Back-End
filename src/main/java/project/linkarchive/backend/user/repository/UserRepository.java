package project.linkarchive.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import project.linkarchive.backend.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialId(String socialId);

    Boolean existsUserByNickname(String nickname);

    Optional<User> findByNickname(String nickname);

    @Modifying
    @Query("UPDATE User u SET u.followingCount = u.followingCount + 1 " +
            "WHERE u.id = :followingId")
    void increaseFollowingCount(Long followingId);

    @Modifying
    @Query("UPDATE User u SET u.followerCount = u.followerCount + 1 " +
            "WHERE u.id = :followerId")
    void increaseFollowerCount(Long followerId);

    @Modifying
    @Query("UPDATE User u SET u.followingCount = u.followingCount - 1 " +
            "WHERE u.id = :followingId")
    void decreaseFollowingCount(Long followingId);

    @Modifying
    @Query("UPDATE User u SET u.followerCount = u.followerCount - 1 " +
            "WHERE u.id = :followerId")
    void decreaseFollowerCount(Long followerId);
}