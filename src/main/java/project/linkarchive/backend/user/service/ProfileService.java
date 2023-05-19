package project.linkarchive.backend.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.S3Uploader;
import project.linkarchive.backend.user.domain.ProfileImage;
import project.linkarchive.backend.user.repository.UserProfileImageRepository;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final S3Uploader s3Uploader;

    private final UserProfileImageRepository userProfileImageRepository;

    @Transactional
    public String saveProfileImage(MultipartFile image, Long userId) throws IOException {

        ProfileImage profileImage = userProfileImageRepository.findByUserId(userId).orElseThrow();
        String storedFileName = s3Uploader.upload(image);
        profileImage.updateProfileImage(storedFileName);

        return storedFileName;
    }
}
