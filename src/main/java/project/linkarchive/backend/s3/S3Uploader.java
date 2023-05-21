package project.linkarchive.backend.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.advice.exception.custom.NotAcceptableException;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.EMPTY_UPLOAD_FILE;

@Service
public class S3Uploader {

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3Uploader(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String upload(MultipartFile multipartFile) throws IOException {
        validateNotEmptyFile(multipartFile);

        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        ObjectMetadata objMeta = new ObjectMetadata();

        String contentType = multipartFile.getContentType();
        if (contentType != null) {
            objMeta.setContentType(contentType);
        }

        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

        return s3FileName;
    }

    public URL generatePresignedUrl(String objectKey, int expirationTimeInMinutes) {
        Date expiration = new Date();
        long mSec = expiration.getTime();
        mSec += expirationTimeInMinutes;
        expiration.setTime(mSec);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, objectKey)
                                                                        .withMethod(HttpMethod.GET)
                                                                        .withExpiration(expiration);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        return url;
    }

    private static void validateNotEmptyFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new NotAcceptableException(EMPTY_UPLOAD_FILE);
        }
    }

}