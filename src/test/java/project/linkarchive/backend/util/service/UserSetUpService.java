package project.linkarchive.backend.util.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.badword.BadWordFiltering;
import project.linkarchive.backend.profileImage.repository.ProfileImageRepository;
import project.linkarchive.backend.s3.S3Uploader;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.user.service.UserApiService;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

@ExtendWith(MockitoExtension.class)
public class UserSetUpService extends SetUpMockData {

    @Mock
    protected UserRepository userRepository;

    @Mock
    protected ProfileImageRepository profileImageRepository;

    @Mock
    protected MultipartFile multipartFile;

    @Mock
    protected BadWordFiltering badWordFiltering;

    @Mock
    protected S3Uploader s3Uploader;

    @InjectMocks
    protected UserApiService userApiService;

}