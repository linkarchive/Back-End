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
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_ACCEPTABLE_CONTENT_TYPE;

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

        String contentType = multipartFile.getContentType();
        ObjectMetadata objMeta = validateContentType(contentType);

        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

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

    private ObjectMetadata validateContentType(String contentType) {
        ObjectMetadata objMeta = new ObjectMetadata();

        if (contentType != null) {
            if (!(contentType.contains("image/jpeg")
                    || contentType.contains("image/jpg")
                    || contentType.contains("image/png"))) {
                throw new NotAcceptableException(NOT_ACCEPTABLE_CONTENT_TYPE);
            }
        }
        objMeta.setContentType(contentType);
        return objMeta;
    }

}