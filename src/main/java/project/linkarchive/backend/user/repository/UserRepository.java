package project.linkarchive.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.linkarchive.backend.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialId(String socialId);

    Boolean existsUserByNickname(String nickname);

    Optional<User> findByNickname(String nickname);

    @Modifying
    @Query("UPDATE User u SET u.followingCount = u.followingCount + 1 " +
            "WHERE u.id = :followerId")
    void increaseFollowingCount(Long followerId);

    @Modifying
    @Query("UPDATE User u SET u.followerCount = u.followerCount + 1 " +
            "WHERE u.id = :followeeId")
    void increaseFollowerCount(Long followeeId);

    @Modifying
    @Query("UPDATE User u SET u.followingCount = u.followingCount - 1 " +
            "WHERE u.id = :followerId")
    void decreaseFollowingCount(Long followerId);

    @Modifying
    @Query("UPDATE User u SET u.followerCount = u.followerCount - 1 " +
            "WHERE u.id = :followeeId")
    void decreaseFollowerCount(Long followeeId);

    @Modifying
    @Query("UPDATE User u SET u.linkCount = u.linkCount + 1 WHERE u.id = :userId")
    void increaseLinkCount(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User u SET u.linkCount = u.linkCount - 1 " +
            "WHERE u.id = :userId")
    void decreaseLinkCount(@Param("userId") Long id);

}