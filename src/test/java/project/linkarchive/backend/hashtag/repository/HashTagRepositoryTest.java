//package project.linkarchive.backend.hashtag.repository;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import project.linkarchive.backend.advice.exception.custom.NotFoundException;
//import project.linkarchive.backend.hashtag.domain.HashTag;
//import project.linkarchive.backend.util.constant.Constants;
//import project.linkarchive.backend.util.repository.HashTagSetUpRepository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_HASHTAG;
//
//class HashTagRepositoryTest extends HashTagSetUpRepository {
//
//    @DisplayName("해시태그 Repository - findByTag")
//    @Test
//    void testFindByTag() {
//        HashTag getHashTag = hashTagRepository.findByTag(Constants.TAG)
//                .orElseThrow(() -> new NotFoundException(NOT_FOUND_HASHTAG));
//
//        Assertions.assertAll(
//                () -> assertNotNull(getHashTag),
//                () -> assertThat(getHashTag.getId()).isEqualTo(hashTag.getId()),
//                () -> assertThat(getHashTag.getTag()).isEqualTo(hashTag.getTag())
//        );
//    }
//
//    @DisplayName("해시태그 Repository - findByTag NotFound")
//    @Test
//    void testFindByTagNotFound() {
//        assertThrows(NotFoundException.class, () -> {
//            hashTagRepository.findByTag(Constants.EMPTY)
//                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_HASHTAG));
//        });
//    }
//
//}