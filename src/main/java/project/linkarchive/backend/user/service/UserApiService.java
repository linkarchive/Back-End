package project.linkarchive.backend.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserApiService {
    private final UserRepository userRepository;

    public User getUserProfile(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new BusinessException(ExceptionCodeConst.NOT_FOUND_USER));
        return user;
    }
}
