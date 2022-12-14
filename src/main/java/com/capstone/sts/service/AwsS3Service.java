package com.capstone.sts.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.capstone.sts.constraint.AwsConst.AWS_S3_BASE_URL;
import static com.capstone.sts.constraint.AwsConst.AWS_S3_BUCKET;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AwsS3Service {

    private final AmazonS3Client amazonS3Client;

    public String uploadFile(String directory, String fileName, MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            objectMetadata.setContentLength(bytes.length);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            amazonS3Client.putObject(
                    new PutObjectRequest(
                            AWS_S3_BUCKET,
                            directory + "/" + fileName,
                            byteArrayInputStream,
                            objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
            return AWS_S3_BASE_URL + "/" + directory + "/" + fileName;
        } catch (IOException e) {
            return null;
        }
    }

}
