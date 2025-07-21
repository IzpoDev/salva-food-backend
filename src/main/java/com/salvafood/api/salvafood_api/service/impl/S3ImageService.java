package com.salvafood.api.salvafood_api.service.impl;

import com.salvafood.api.salvafood_api.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.UUID;

@Service
@ConditionalOnProperty(name = "app.image.storage", havingValue = "s3")
public class S3ImageService implements ImageStorageService {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.access-key}")
    private String accessKey;

    @Value("${aws.s3.secret-key}")
    private String secretKey;

    private S3Client getS3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Override
    public String saveImage(MultipartFile file, Long productId) throws IOException {
        // Validaciones
        if (file.isEmpty()) {
            throw new IOException("El archivo está vacío");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("Solo se permiten archivos de imagen");
        }

        // Generar nombre único
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = "products/" + productId + "/" + UUID.randomUUID() + fileExtension;

        try (S3Client s3Client = getS3Client()) {
            // Subir archivo a S3
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .contentType(contentType)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            // Retornar URL pública
            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, filename);
        }
    }

    @Override
    public void deleteImage(String imageUrl) throws IOException {
        if (imageUrl != null && imageUrl.contains(bucketName)) {
            // Extraer la key del objeto desde la URL
            String key = imageUrl.substring(imageUrl.indexOf(".com/") + 5);

            try (S3Client s3Client = getS3Client()) {
                DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build();

                s3Client.deleteObject(deleteObjectRequest);
            }
        }
    }
}