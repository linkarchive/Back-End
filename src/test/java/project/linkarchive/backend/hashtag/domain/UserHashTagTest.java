package project.linkarchive.backend.hashtag.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.util.setUpData.UserHashTagSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.linkarchive.backend.util.constant.Constants.USAGE_COUNT;

class UserHashTagTest extends UserHashTagSetUpData {

    @DisplayName("유저 해시태그 getUsageCount - Domain")
    @Test
    void testGetUsageCount() {
        Long usageCount = userHashTag.getUsageCount();

        assertNotNull(usageCount);
        assertEquals(USAGE_COUNT, usageCount);
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
        HashTag getHashTag = userHashTag.getHashTag();

        assertNotNull(getHashTag);
        assertEquals(hashTag, getHashTag);
    }

    @DisplayName("유저 해시태그 Builder 패턴 - Domain")
    @Test
    void testBuilder() {
        UserHashTag getUserHashTag = UserHashTag.builder()
                .usageCount(USAGE_COUNT)
                .user(user)
                .hashTag(hashTag)
                .build();

        assertNotNull(getUserHashTag);
        assertEquals(USAGE_COUNT, getUserHashTag.getUsageCount());
        assertEquals(user, getUserHashTag.getUser());
        assertEquals(hashTag, getUserHashTag.getHashTag());
    }

//    @DisplayName("유저 해시태그 Build 메서드 - Domain")
//    @Test
//    void testBuild() {
//        UserHashTag getUserHashTag = UserHashTag.create(linkHashTagList, user, hashTag);
//
//        assertNotNull(getUserHashTag);
//        assertEquals(linkHashTagList.size(), getUserHashTag.getUsageCount());
//        assertEquals(user, getUserHashTag.getUser());
//        assertEquals(hashTag, getUserHashTag.getHashTag());
//    }

}