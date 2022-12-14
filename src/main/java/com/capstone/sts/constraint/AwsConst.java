package com.capstone.sts.constraint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AwsConst {

    public static String AWS_S3_BUCKET;

    public static String AWS_S3_BASE_URL;

    @Value("${aws.s3.bucket}")
    public void setAwsS3Bucket(String awsS3Bucket) {
        AWS_S3_BUCKET = awsS3Bucket;
    }

    @Value("${aws.s3.base.url}")
    public void setAwsS3BaseUrl(String awsS3BaseUrl) {
        AWS_S3_BASE_URL = awsS3BaseUrl;
    }

}
