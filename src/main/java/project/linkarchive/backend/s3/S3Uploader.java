package project.linkarchive.backend.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.advice.exception.custom.NotAcceptableException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static project.linkarchive.backend.advice.data.DataConstants.S3_KEY;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.CONVERSION_FAILED;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_ACCEPTABLE_CONTENT_TYPE;

@Service
@Log4j2
public class S3Uploader {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3Uploader(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String upload(MultipartFile multipartFile) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new NotAcceptableException(CONVERSION_FAILED));

        return upload(uploadFile);
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    private String upload(File uploadFile) {
        String fileName = UUID.randomUUID().toString();
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);

        return extractKey(uploadImageUrl);
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    public URL generatePresignedProfileImageUrl(String objectKey, int expirationTimeInMinutes) {
        Date expiration = new Date();
        long mSec = expiration.getTime();
        mSec += expirationTimeInMinutes;
        expiration.setTime(mSec);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, objectKey)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
    }

    public void deleteFile(String key) {
        try {
            amazonS3.deleteObject(bucket, key);
        } catch (SdkClientException e) {
            throw new NotAcceptableException(NOT_ACCEPTABLE_CONTENT_TYPE);
        }
    }

    private String extractKey(String fileName) {
        return fileName.split("/")[S3_KEY];
    }

}