package project.linkarchive.backend.hashtag.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.HashTagSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.linkarchive.backend.util.constant.Constants.TAG;

class HashTagTest extends HashTagSetUpData {

    @DisplayName("해시태그 getTag - Domain")
    @Test
    void testGetTag() {
        String tag = hashTag.getTag();

        assertNotNull(tag);
        assertEquals(TAG, tag);
    }

    @DisplayName("해시태그 Builder 패턴 - Domain")
    @Test
    void testBuilder() {
        HashTag getHashTag = HashTag.builder()
                .tag(TAG)
                .build();

        assertNotNull(getHashTag);
        assertEquals(TAG, getHashTag.getTag());
    }

    @DisplayName("해시태그 Build 메서드 - Domain")
    @Test
    void testBuild() {
        HashTag getHashTag = HashTag.build(createTagRequest);

        assertNotNull(getHashTag);
        assertEquals(TAG, getHashTag.getTag());
    }

}