package project.linkarchive.backend.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.advice.exception.custom.NotAcceptableException;

import java.io.IOException;
import java.util.UUID;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.EMPTY_UPLOAD_FILE;

@Service
public class S3Uploader {

    private final AmazonS3 amazonS3;

    public S3Uploader(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            throw new NotAcceptableException(EMPTY_UPLOAD_FILE);
        }

        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

}