package project.linkarchive.backend.bookmark.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.ExceededException;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.bookmark.repository.BookMarkRepository;
import project.linkarchive.backend.bookmark.repository.BookMarkRepositoryImpl;
import project.linkarchive.backend.bookmark.response.MarkResponse;
import project.linkarchive.backend.bookmark.response.UserMarkListResponse;
import project.linkarchive.backend.bookmark.response.UserMarkResponse;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.isLinkRead.repository.IsLinkReadRepository;
import project.linkarchive.backend.link.domain.LinkHashTag;
import project.linkarchive.backend.link.repository.LinkHashTagRepository;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.EXCEEDED_TAG_SIZE;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

@Service
@Transactional(readOnly = true)
public class BookMarkQueryService {

    public static final Long MAX_SIZE = 30L;

    private final UserRepository userRepository;
    private final BookMarkRepository bookMarkRepository;
    private final LinkHashTagRepository linkHashTagRepository;
    private final IsLinkReadRepository isLinkReadRepository;
    private final BookMarkRepositoryImpl bookMarkRepositoryImpl;

    public BookMarkQueryService(UserRepository userRepository, BookMarkRepository bookMarkRepository, LinkHashTagRepository linkHashTagRepository, IsLinkReadRepository isLinkReadRepository, BookMarkRepositoryImpl bookMarkRepositoryImpl) {
        this.userRepository = userRepository;
        this.bookMarkRepository = bookMarkRepository;
        this.linkHashTagRepository = linkHashTagRepository;
        this.isLinkReadRepository = isLinkReadRepository;
        this.bookMarkRepositoryImpl = bookMarkRepositoryImpl;
    }

    public UserMarkListResponse getMyMarkLinkList(Long userId, Long lastMarkId, Pageable pageable, String tag) {
        validateUserById(userId);

        List<MarkResponse> markResponseList = bookMarkRepositoryImpl.getMyMarkLinkList(userId, lastMarkId, pageable, tag);

        boolean hasNext = isHasNextLinkList(pageable, markResponseList);

        List<UserMarkResponse> userMarkResponseList = markResponseList.stream()
                .map(markResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(markResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::build)
                            .collect(Collectors.toList());

                    Boolean isRead = isLinkReadRepository.existsByLinkIdAndUserId(markResponse.getLinkId(), userId);
                    Boolean isMark = bookMarkRepository.existsByLinkIdAndUserId(markResponse.getLinkId(), userId);

                    return UserMarkResponse.build(markResponse, isRead, isMark, tagList);
                }).collect(Collectors.toList());

        return new UserMarkListResponse(userMarkResponseList, hasNext);
    }

    public UserMarkListResponse getPublicUserMarkedLinkList(String nickname, Long lastMarkId, Pageable pageable, String tag) {
        validateUserByNickname(nickname);

        List<MarkResponse> markResponseList = bookMarkRepositoryImpl.getUserMarkLinkList(nickname, lastMarkId, pageable, tag);

        boolean hasNext = isHasNextLinkList(pageable, markResponseList);

        List<UserMarkResponse> userMarkResponseList = markResponseList.stream()
                .map(markResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(markResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::build)
                            .collect(Collectors.toList());

                    return UserMarkResponse.build(markResponse, false, false, tagList);
                }).collect(Collectors.toList());

        return new UserMarkListResponse(userMarkResponseList, hasNext);
    }

    public UserMarkListResponse getAuthenticatedUserMarkedLinkList(String nickname, Long lastMarkId, Pageable pageable, Long loginUserId, String tag) {
        validateUserByNickname(nickname);

        List<MarkResponse> markResponseList = bookMarkRepositoryImpl.getUserMarkLinkList(nickname, lastMarkId, pageable, tag);

        boolean hasNext = isHasNextLinkList(pageable, markResponseList);

        List<UserMarkResponse> userMarkResponseList = markResponseList.stream()
                .map(markResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(markResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::build)
                            .collect(Collectors.toList());

                    Boolean isRead = isLinkReadRepository.existsByLinkIdAndUserId(markResponse.getLinkId(), loginUserId);
                    Boolean isMark = bookMarkRepository.existsByLinkIdAndUserId(markResponse.getLinkId(), loginUserId);

                    return UserMarkResponse.build(markResponse, isRead, isMark, tagList);
                }).collect(Collectors.toList());

        return new UserMarkListResponse(userMarkResponseList, hasNext);
    }

    public TagListResponse getMarkTagList(String nickname) {
        User user = getUserByNickname(nickname);

        List<BookMark> bookMarkList = bookMarkRepository.findByUserId(user.getId());

        Map<String, Integer> tagCountMap = new HashMap<>();
        bookMarkList.forEach(bookMark -> {
            List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(bookMark.getLink().getId());
            linkHashTagList.forEach(urlHashTag -> {
                String tag = urlHashTag.getHashTag().getTag();
                tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
            });
        });

        List<TagResponse> tagList = tagCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(entry -> new TagResponse(entry.getKey()))
                .collect(Collectors.toList());

        return new TagListResponse(tagList);
    }

    public TagListResponse getMarkTagLimitList(String nickname, Long size) {
        User user = getUserByNickname(nickname);
        validateSizeDoesNotExceedMax(size);
        List<BookMark> bookMarkList = bookMarkRepository.findByUserId(user.getId());

        Map<String, Integer> tagCountMap = new HashMap<>();
        bookMarkList.forEach(bookMark -> {
            List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(bookMark.getLink().getId());
            linkHashTagList.forEach(urlHashTag -> {
                String tag = urlHashTag.getHashTag().getTag();
                tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
            });
        });

        List<TagResponse> tagList = tagCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(size)
                .map(entry -> new TagResponse(entry.getKey()))
                .collect(Collectors.toList());

        return new TagListResponse(tagList);
    }

    private void validateUserById(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private void validateUserByNickname(String nickname) {
        userRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private void validateSizeDoesNotExceedMax(Long size) {
        if (size > MAX_SIZE) {
            throw new ExceededException(EXCEEDED_TAG_SIZE);
        }
    }

    private boolean isHasNextLinkList(Pageable pageable, List<MarkResponse> linkResponseList) {
        boolean hasNext = false;
        if (linkResponseList.size() > pageable.getPageSize()) {
            linkResponseList.remove(linkResponseList.size() - 1);
            hasNext = true;
        }
        return hasNext;
    }

}