package ua.faculty.faculty_student.Config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {
  private final String awsAccessKey = "AKIAZWTJALMGSCGYXREI";
  private final String awsSecretKey = "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";
  private final String region = "eu-north-1";

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
