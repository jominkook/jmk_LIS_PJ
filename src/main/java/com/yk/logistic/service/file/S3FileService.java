package com.yk.logistic.service.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import java.nio.file.Paths;

@Service
public class S3FileService {

    private final S3Client s3Client;
    private final String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public S3FileService(@Value("${cloud.aws.credentials.access-key}") String accessKey,
                         @Value("${cloud.aws.credentials.secret-key}") String secretKey,
                         @Value("${cloud.aws.region.static}") String region) {
        this.region = region; // 리전을 저장
        this.s3Client = S3Client.builder()
                .region(Region.of(region)) // 리전을 명시적으로 설정
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .build();
    }

    public String uploadFile(MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            System.out.println("Uploading file: " + fileName);

            // S3에 파일 업로드
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    //.acl("public-read")
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
        } catch (Exception e) {
            e.printStackTrace(); // 예외 스택 트레이스 출력
            throw new RuntimeException("파일 업로드 중 알 수 없는 오류 발생", e);
        }
    }
}