package project.linkarchive.backend.util.setUpData;

import org.junit.jupiter.api.BeforeEach;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.hashtag.request.CreateTagRequest;

import static project.linkarchive.backend.util.constant.Constants.TAG;

public class HashTagSetUpData {

    public HashTag hashTag;

    public CreateTagRequest createTagRequest;

    @BeforeEach
    public void setup() {

        hashTag = HashTag.builder()
                .tag(TAG)
                .build();

        createTagRequest = new CreateTagRequest(TAG);

    }

}