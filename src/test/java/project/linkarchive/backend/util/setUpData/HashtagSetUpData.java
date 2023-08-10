package project.linkarchive.backend.util.setUpData;

import org.junit.jupiter.api.BeforeEach;
import project.linkarchive.backend.hashtag.domain.Hashtag;
import project.linkarchive.backend.hashtag.request.CreateTagRequest;

import static project.linkarchive.backend.util.constant.Constants.TAG;

public class HashtagSetUpData extends MockDataGenerator {

    @BeforeEach
    public void setup() {

        hashTag = Hashtag.builder()
                .tag(TAG)
                .build();

        createTagRequest = new CreateTagRequest(TAG);

    }

}