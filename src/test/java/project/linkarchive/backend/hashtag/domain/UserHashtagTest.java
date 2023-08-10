package project.linkarchive.backend.hashtag.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.util.setUpData.UserHashtagSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.linkarchive.backend.util.constant.Constants.USAGE_COUNT;
import static project.linkarchive.backend.util.constant.Constants.USAGE_COUNT_INT;

class UserHashtagTest extends UserHashtagSetUpData {

    @DisplayName("유저 해시태그 getUsageCount - Domain")
    @Test
    void testGetUsageCount() {
        int usageCount = userHashTag.getUsageCount();

        assertNotNull(usageCount);
        assertEquals(USAGE_COUNT_INT, usageCount);
    }

    @DisplayName("유저 해시태그 getUser - Domain")
    @Test
    void testGetUser() {
        User getUser = userHashTag.getUser();

        assertNotNull(getUser);
        assertEquals(user, getUser);
    }

    @DisplayName("유저 해시태그 getHashTag - Domain")
    @Test
    void testGetHashTag() {
        Hashtag getHashtag = userHashTag.getHashtag();

        assertNotNull(getHashtag);
        assertEquals(hashTag, getHashtag);
    }

    @DisplayName("유저 해시태그 Builder 패턴 - Domain")
    @Test
    void testBuilder() {
        UserHashtag getUserHashtag = UserHashtag.builder()
                .usageCount(USAGE_COUNT_INT)
                .user(user)
                .hashtag(hashTag)
                .build();

        assertNotNull(getUserHashtag);
        assertEquals(USAGE_COUNT_INT, getUserHashtag.getUsageCount());
        assertEquals(user, getUserHashtag.getUser());
        assertEquals(hashTag, getUserHashtag.getHashtag());
    }

}