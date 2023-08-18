package ua.faculty.faculty_student.Config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {
  @Value("${aws.accessKeyId}")
  private String awsAccessKey;

  @Value("${aws.secretKey}")
  private String awsSecretKey;

  @Value("${aws.region}")
  private String region;

  @Bean
  public AmazonS3 amazonS3Client() {
    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);

    return AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
            "s3." + region + ".amazonaws.com", region))
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .build();
  }
}
