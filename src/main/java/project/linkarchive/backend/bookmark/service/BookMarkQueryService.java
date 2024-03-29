package project.linkarchive.backend.bookmark.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.bookmark.domain.Bookmark;
import project.linkarchive.backend.bookmark.repository.BookMarkRepository;
import project.linkarchive.backend.bookmark.repository.BookMarkRepositoryImpl;
import project.linkarchive.backend.bookmark.response.MarkResponse;
import project.linkarchive.backend.bookmark.response.UserMarkListResponse;
import project.linkarchive.backend.bookmark.response.UserMarkResponse;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.isLinkRead.repository.IsLinkReadRepository;
import project.linkarchive.backend.link.domain.LinkHashtag;
import project.linkarchive.backend.link.repository.LinkHashTagRepository;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static project.linkarchive.backend.advice.data.DataConstants.TAG_SIZE;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

@Service
@Transactional(readOnly = true)
public class BookMarkQueryService {

    private final UserRepository userRepository;
    private final BookMarkRepository bookMarkRepository;
    private final LinkHashTagRepository linkHashTagRepository;
    private final IsLinkReadRepository isLinkReadRepository;
    private final BookMarkRepositoryImpl bookMarkRepositoryImpl;

    public BookMarkQueryService(
            UserRepository userRepository,
            BookMarkRepository bookMarkRepository,
            LinkHashTagRepository linkHashTagRepository,
            IsLinkReadRepository isLinkReadRepository,
            BookMarkRepositoryImpl bookMarkRepositoryImpl
    ) {
        this.userRepository = userRepository;
        this.bookMarkRepository = bookMarkRepository;
        this.linkHashTagRepository = linkHashTagRepository;
        this.isLinkReadRepository = isLinkReadRepository;
        this.bookMarkRepositoryImpl = bookMarkRepositoryImpl;
    }

    public UserMarkListResponse getMyMarkedLinkList(Long tagId, Long markId, Pageable pageable, Long userId) {
        getUserById(userId);

        List<MarkResponse> markResponseList = bookMarkRepositoryImpl.getMyMarkLinkList(tagId, markId, pageable, userId);
        List<UserMarkResponse> userMarkResponseList = markResponseList.stream()
                .map(markResponse -> {
                    List<LinkHashtag> linkHashtagList = linkHashTagRepository.findByLinkId(markResponse.getLinkId());
                    List<TagResponse> tagList = linkHashtagList.stream()
                            .map(TagResponse::create)
                            .collect(Collectors.toList());

                    Boolean isRead = isLinkReadRepository.existsByLinkIdAndUserId(markResponse.getLinkId(), userId);
                    Boolean isMark = bookMarkRepository.existsByLinkIdAndUserId(markResponse.getLinkId(), userId);

                    return UserMarkResponse.create(markResponse, isRead, isMark, tagList);
                }).collect(Collectors.toList());

        boolean hasNext = isHasNextLinkList(pageable, markResponseList);

        return new UserMarkListResponse(userMarkResponseList, hasNext);
    }

    public UserMarkListResponse getUserMarkedLinkList(Long userId, Long tagId, Long markId, Pageable pageable, AuthInfo authInfo) {
        getUserById(userId);

        Long loginUserId = authInfo != null ? authInfo.getId() : null;

        List<MarkResponse> markResponseList = bookMarkRepositoryImpl.getUserMarkLinkList(userId, tagId, markId, pageable);
        List<UserMarkResponse> userMarkResponseList = markResponseList.stream()
                .map(markResponse -> {
                    List<LinkHashtag> linkHashtagList = linkHashTagRepository.findByLinkId(markResponse.getLinkId());
                    List<TagResponse> tagList = linkHashtagList.stream()
                            .map(TagResponse::create)
                            .collect(Collectors.toList());

                    Boolean isRead = (loginUserId != null) ? isLinkReadRepository.existsByLinkIdAndUserId(markResponse.getLinkId(), loginUserId) : false;
                    Boolean isMark = (loginUserId != null) ? bookMarkRepository.existsByLinkIdAndUserId(markResponse.getLinkId(), loginUserId) : false;

                    return UserMarkResponse.create(markResponse, isRead, isMark, tagList);
                }).collect(Collectors.toList());

        boolean hasNext = isHasNextLinkList(pageable, markResponseList);

        return new UserMarkListResponse(userMarkResponseList, hasNext);
    }

    public TagListResponse getMarkTagList(Long userId) {
        User user = findUserById(userId);

        List<Bookmark> bookmarkList = bookMarkRepository.findByUserId(user.getId());
        Map<String, Long> tagIdMap = new HashMap<>();
        Map<String, Integer> tagCountMap = new HashMap<>();

        bookmarkList.forEach(bookMark -> {
            List<LinkHashtag> linkHashtagList = linkHashTagRepository.findByLinkId(bookMark.getLink().getId());
            linkHashtagList.forEach(urlHashTag -> {
                Long tagId = urlHashTag.getHashtag().getId();
                String tagName = urlHashTag.getHashtag().getTag();
                tagIdMap.put(tagName, tagId);
                tagCountMap.put(tagName, tagCountMap.getOrDefault(tagName, 0) + 1);
            });
        });

        List<TagResponse> tagList = tagCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(entry -> new TagResponse(tagIdMap.get(entry.getKey()), entry.getKey()))
                .collect(Collectors.toList());

        return new TagListResponse(tagList);
    }

    public TagListResponse getMarkTagList10(Long userId) {
        User user = findUserById(userId);

        List<Bookmark> bookmarkList = bookMarkRepository.findByUserId(user.getId());
        Map<String, Long> tagIdMap = new HashMap<>();
        Map<String, Integer> tagCountMap = new HashMap<>();

        bookmarkList.forEach(bookMark -> {
            List<LinkHashtag> linkHashtagList = linkHashTagRepository.findByLinkId(bookMark.getLink().getId());
            linkHashtagList.forEach(urlHashTag -> {
                Long tagId = urlHashTag.getHashtag().getId();
                String tagName = urlHashTag.getHashtag().getTag();
                tagIdMap.put(tagName, tagId);
                tagCountMap.put(tagName, tagCountMap.getOrDefault(tagName, 0) + 1);
            });
        });

        List<TagResponse> tagList = tagCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(TAG_SIZE)
                .map(entry -> new TagResponse(tagIdMap.get(entry.getKey()), entry.getKey()))
                .collect(Collectors.toList());

        return new TagListResponse(tagList);
    }

    private void getUserById(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
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