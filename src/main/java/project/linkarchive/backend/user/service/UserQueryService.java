package project.linkarchive.backend.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.user.response.UserProfileResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryService {
    private final UserRepository userRepository;

    public UserProfileResponse getUserProfile(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(()->new BusinessException(ExceptionCodeConst.NOT_FOUND_USER));
        return new UserProfileResponse(user);
    }
}
